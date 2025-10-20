package org.jeongmo.migration.auth.application.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.jeongmo.migration.auth.application.dto.TokenInfoDTO
import org.jeongmo.migration.auth.application.error.code.TokenErrorCode
import org.jeongmo.migration.auth.application.error.exception.TokenException
import org.jeongmo.migration.auth.domain.model.CustomUserDetails
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
): TokenUtil {

    private val logger = LoggerFactory.getLogger(JwtTokenUtil::class.java)

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8)) // 지정하지 않으면 OS에 따라 다르게 작동
    private val issuer = "org.jeongmo.migration"
    private val clock: Long = 10

    override fun createToken(userDetails: CustomUserDetails, expiration: Long): String {
        val now: Instant = Instant.now()
        return Jwts.builder()
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
        val idPayload = claims.payload["id"]
        val username = claims.payload.subject
        val roles = getAuthorities(claims)

        return TokenInfoDTO(
            id = idPayload.toString(),
            username = username,
            roles = roles,
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
            throw TokenException(TokenErrorCode.TOKEN_EXPIRED)
        } catch (e: Exception) {
            logger.warn("Invalid Token", e)
            throw TokenException(TokenErrorCode.TOKEN_NOT_VALID)
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