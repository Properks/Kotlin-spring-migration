package org.jeongmo.migration.auth.application.service

import org.jeongmo.migration.auth.application.dto.*
import org.jeongmo.migration.auth.application.error.code.AuthErrorCode
import org.jeongmo.migration.auth.application.error.exception.AuthException
import org.jeongmo.migration.auth.application.port.`in`.AuthCommandUseCase
import org.jeongmo.migration.auth.application.port.out.member.MemberServiceClient
import org.jeongmo.migration.auth.application.port.out.member.dto.CreateMemberRequest
import org.jeongmo.migration.auth.application.port.out.member.dto.VerifyMemberRequest
import org.jeongmo.migration.auth.application.port.out.member.enums.ProviderType
import org.jeongmo.migration.auth.application.port.out.member.enums.Role
import org.jeongmo.migration.common.token.application.error.code.TokenErrorCode
import org.jeongmo.migration.common.token.application.error.exception.TokenException
import org.jeongmo.migration.common.token.domain.model.CustomUserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class AuthService(
    private val tokenAuthService: TokenAuthService,
    private val memberServiceClient: MemberServiceClient,
): AuthCommandUseCase {

    override fun signUp(request: SignUpRequest) {
        val clientRequest = CreateMemberRequest(
            username = request.username,
            password = request.password,
            nickname = request.nickname,
            providerType = ProviderType.LOCAL,
            role = Role.USER
            )
        memberServiceClient.createMember(clientRequest)
    }

    override fun login(request: LoginRequest): LoginResponse {
        val clientRequest = VerifyMemberRequest(request.username, request.password, ProviderType.LOCAL)
        val memberInfo = memberServiceClient.verifyMember(clientRequest) ?: throw AuthException(AuthErrorCode.UNAUTHORIZED_DATA)
        val userDetails = CustomUserDetails(
            id = memberInfo.id,
            username = memberInfo.username,
            password = "",
            roles = Collections.singletonList(memberInfo.role.name)
        )
        return processLogin(userDetails)
    }

    override fun reissueToken(request: ReissueTokenRequest): ReissueTokenResponse {
        val tokenInfo = tokenAuthService.getTokenInfo(request.refreshToken)
        val userDetails = CustomUserDetails(
            id = tokenInfo.id.toLongOrNull() ?: throw TokenException(TokenErrorCode.TOKEN_NOT_VALID),
            username = tokenInfo.username,
            password = "",
            roles = tokenInfo.roles.map {it.authority},
        )
        return ReissueTokenResponse(
            accessToken = tokenAuthService.createAccessToken(userDetails)
        )
    }

    private fun processLogin(userDetails: CustomUserDetails): LoginResponse {
        val accessToken = tokenAuthService.createAccessToken(userDetails)
        val refreshToken = tokenAuthService.createRefreshToken(userDetails)
        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            role = userDetails.authorities.map {it.authority},
            loginTime = LocalDateTime.now(),
        )
    }
}