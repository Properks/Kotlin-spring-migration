package org.jeongmo.migration.common.token.application.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.jeongmo.migration.common.token.application.constants.TokenType
import org.jeongmo.migration.common.token.domain.model.CustomUserDetails
import org.jeongmo.migration.common.token.application.dto.TokenInfoDTO
import org.jeongmo.migration.common.token.application.error.code.TokenErrorCode
import org.jeongmo.migration.common.token.application.error.exception.TokenException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenUtil(
    @Value("\${token.jwt.secret}") secret: String,
    @Value("\${token.jwt.issuer:org.jeongmo.migration}") private val issuer: String,
    @Value("\${token.jwt.clock-skew:10}") private val clock: Long,
): TokenUtil {

    private val logger = LoggerFactory.getLogger(JwtTokenUtil::class.java)
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8)) // 지정하지 않으면 OS에 따라 다르게 작동

    override fun createToken(userDetails: CustomUserDetails, expiration: Long, type: TokenType): String {
        val now: Instant = Instant.now()
        return Jwts.builder()
            .header().type(type.name).and()
            .subject(userDetails.username)
            .claim("id", userDetails.id)
            .claim("roles", userDetails.authorities.map {it.authority})
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusMillis(expiration)))
            .signWith(secretKey)
            .compact()
    }

    override fun parseToken(token: String): TokenInfoDTO {
        val claims: Jws<Claims> = getClaims(token)
        val type = try {
            TokenType.valueOf(claims.header.type)
        } catch (e: Exception) {
            logger.warn("Invalid Token Type: ${claims.header.type}", e)
            throw TokenException(TokenErrorCode.INVALID_TOKEN_TYPE, e)
        }
        val idPayload = claims.payload["id"]?.toString() ?: throw TokenException(TokenErrorCode.FAIL_READ_TOKEN)
        val username = claims.payload.subject
        val roles = getAuthorities(claims)

        return TokenInfoDTO(
            id = idPayload,
            username = username,
            roles = roles,
            type = type,
        )
    }

    private fun getClaims(token: String): Jws<Claims> {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .clockSkewSeconds(clock)
                .build()
                .parseSignedClaims(token)
        } catch (e: ExpiredJwtException) {
            logger.debug("Token expired", e)
            throw TokenException(TokenErrorCode.TOKEN_EXPIRED, e)
        } catch (e: Exception) {
            logger.warn("Invalid Token", e)
            throw TokenException(TokenErrorCode.TOKEN_NOT_VALID, e)
        }
    }

    private fun getAuthorities(claims: Jws<Claims>): MutableCollection<out GrantedAuthority> {
        val rawRolesList: List<*> = claims.payload["roles", List::class.java] ?: emptyList<Any>()

        return rawRolesList
            .filterIsInstance<String>()
            .map { SimpleGrantedAuthority(it) }
            .toMutableSet()
    }
}