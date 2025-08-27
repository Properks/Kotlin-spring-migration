package org.jeongmo.practice.global.security.token.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import org.jeongmo.practice.global.data.JwtConfigData
import org.jeongmo.practice.global.error.code.TokenErrorCode
import org.jeongmo.practice.global.error.exception.TokenException
import org.jeongmo.practice.global.security.token.util.TokenUtil
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class JwtTokenService(
    private val jwtUtil: TokenUtil<Jws<Claims>>,
    private val configData: JwtConfigData
): TokenService {


    override fun createAccessToken(memberInfo: UserDetails): String {
        return jwtUtil.createToken(memberInfo, configData.time.accessToken)
    }

    override fun createRefreshToken(memberInfo: UserDetails): String {
        return jwtUtil.createToken(memberInfo, configData.time.refreshToken)
    }

    override fun isValid(token: String): Boolean {
        try {
            jwtUtil.getClaims(token).payload.subject
            return true
        } catch (e: ExpiredJwtException) {
            throw TokenException(TokenErrorCode.TOKEN_TIME_EXPIRED)
        } catch (e: JwtException) {
            throw e
        }
    }

    override fun getSubject(token: String): String {
        return jwtUtil.getClaims(token).payload.subject
    }
}