package org.jeongmo.migration.auth.application.service

import org.jeongmo.migration.common.token.application.constants.TokenType
import org.jeongmo.migration.common.token.application.dto.TokenInfoDTO
import org.jeongmo.migration.common.token.application.util.TokenUtil
import org.jeongmo.migration.common.token.domain.model.CustomUserDetails
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JwtTokenAuthService(
    @Value("\${token.jwt.expiration-time.access-token}") private val accessTokenExpiration: Long,
    @Value("\${token.jwt.expiration-time.refresh-token}") private val refreshTokenExpiration: Long,
    private val tokenUtil: TokenUtil,
): TokenAuthService {

    override fun createAccessToken(userDetails: CustomUserDetails): String {
        return tokenUtil.createToken(
            userDetails = userDetails,
            expiration = accessTokenExpiration,
            type = TokenType.ACCESS,
        )
    }

    override fun createRefreshToken(userDetails: CustomUserDetails): String {
        return tokenUtil.createToken(
            userDetails = userDetails,
            expiration = refreshTokenExpiration,
            type = TokenType.REFRESH,
        )
    }

    override fun getTokenInfo(token: String): TokenInfoDTO {
        return tokenUtil.parseToken(token)
    }
}