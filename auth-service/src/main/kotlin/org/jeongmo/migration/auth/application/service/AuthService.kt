package org.jeongmo.migration.auth.application.service

import org.jeongmo.migration.auth.application.dto.*
import org.jeongmo.migration.auth.application.error.code.AuthErrorCode
import org.jeongmo.migration.auth.application.error.exception.AuthException
import org.jeongmo.migration.auth.application.port.inbound.AuthCommandUseCase
import org.jeongmo.migration.auth.application.port.out.member.MemberServiceClient
import org.jeongmo.migration.auth.application.port.out.member.dto.CreateMemberRequest
import org.jeongmo.migration.auth.application.port.out.member.dto.VerifyMemberRequest
import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.common.enums.member.Role
import org.jeongmo.migration.common.token.application.constants.TokenType
import org.jeongmo.migration.common.token.application.error.code.TokenErrorCode
import org.jeongmo.migration.common.token.application.error.exception.TokenException
import org.jeongmo.migration.common.token.domain.model.CustomUserDetails
import org.jeongmo.migration.common.token.domain.repository.TokenRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.math.log

@Service
class AuthService(
    private val tokenAuthService: TokenAuthService,
    private val memberServiceClient: MemberServiceClient,
    private val tokenRepository: TokenRepository,
): AuthCommandUseCase {

    private val logger = LoggerFactory.getLogger(AuthService::class.java)

    override fun signUp(request: SignUpRequest) {
        val clientRequest = CreateMemberRequest(
            username = request.username,
            password = request.password,
            nickname = request.nickname,
            providerType = ProviderType.LOCAL,
            role = Role.USER
            )
        memberServiceClient.createMember(clientRequest).also { logger.info("[SUCCESS_SIGN_UP] auth-service") }
    }

    override fun login(request: LoginRequest): LoginResponse {
        val clientRequest = VerifyMemberRequest(request.username, request.password, ProviderType.LOCAL)
        val memberInfo = memberServiceClient.verifyMember(clientRequest)
        val userDetails = CustomUserDetails(
            id = memberInfo.id,
            username = memberInfo.username,
            password = "",
            roles = Collections.singletonList(memberInfo.role.name)
        )

        return processLogin(userDetails).also { logger.info("[SUCCESS_LOGIN] auth-service") }
    }

    override fun reissueToken(request: ReissueTokenRequest): ReissueTokenResponse {
        val tokenInfo = tokenAuthService.getTokenInfo(request.refreshToken)
        if (tokenRepository.isBlackList(request.refreshToken) ||
            tokenRepository.getToken(
                tokenInfo.id.toLongOrNull() ?: throw TokenException(TokenErrorCode.TOKEN_NOT_VALID),
                TokenType.REFRESH
            ) != request.refreshToken) {
            throw TokenException(TokenErrorCode.CANNOT_REISSUE)
        }

        if (tokenInfo.type != TokenType.REFRESH) throw TokenException(TokenErrorCode.INVALID_TOKEN_TYPE)
        val userDetails = CustomUserDetails(
            id = tokenInfo.id.toLongOrNull() ?: throw TokenException(TokenErrorCode.TOKEN_NOT_VALID),
            username = tokenInfo.username,
            password = "",
            roles = tokenInfo.roles.map {it.authority},
        )
        return ReissueTokenResponse(
            accessToken = tokenAuthService.createAccessToken(userDetails)
        ).also { logger.info("[SUCCESS_REISSUE_TOKEN] auth-service") }
    }

    override fun authorize(token: String): AuthorizeResponse {
        try {
            val info = tokenAuthService.getTokenInfo(token)
            return AuthorizeResponse(
                id = info.id.toLongOrNull() ?: throw AuthException(AuthErrorCode.FAIL_TO_VERIFY),
                roles = info.roles,
            )
        } catch (e: TokenException) {
            logger.warn("[FAIL_TO_AUTHORIZE_TOKEN] auth-service")
            throw AuthException(AuthErrorCode.UNAUTHORIZED_DATA, e)
        }
        catch (e: Exception) {
            logger.error("[FAIL_TO_VERIFY_TOKEN] auth-service")
            throw AuthException(AuthErrorCode.FAIL_TO_VERIFY, e)
        }
    }

    private fun processLogin(userDetails: CustomUserDetails): LoginResponse {
        val accessToken = tokenAuthService.createAccessToken(userDetails)
        val refreshToken = tokenAuthService.createRefreshToken(userDetails)
        val savedRefreshToken = if (!tokenRepository.saveToken(userDetails.id, refreshToken, TokenType.REFRESH)) {
            logger.warn("[FAIL_TO_SAVE_TOKEN] auth-service")
            null
        } else { refreshToken }
        return LoginResponse(
            accessToken = accessToken,
            refreshToken = savedRefreshToken,
            role = userDetails.authorities.map {it.authority},
            loginTime = LocalDateTime.now(),
        )
    }
}