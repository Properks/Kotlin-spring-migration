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
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenUtil(
    @Value("\${token.jwt.secret}") secret: String,
): TokenUtil {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    private val issuer = "org.jeongmo.migration"
    private val clock: Long = 10

    override fun createToken(userDetails: CustomUserDetails, expiration: Long): String {
        val now: Instant = Instant.now()
        return Jwts.builder()
            .subject(userDetails.username)
            .claim("id", userDetails.id)
            .claim("roles", userDetails.authorities)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusMillis(expiration)))
            .signWith(secretKey)
            .compact()
    }

    override fun parseToken(token: String): TokenInfoDTO {
        val claims: Jws<Claims> = getClaims(token)
        val id: Long = claims.payload["id", Long::class.java]
        val username = claims.payload.subject

        val roles: List<String> = claims.payload["roles", List::class.java] as? List<String> ?: emptyList()
        return TokenInfoDTO(
            id = id,
            username = username,
            roles = roles.map { SimpleGrantedAuthority(it) }.toMutableSet(),
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
            throw TokenException(TokenErrorCode.TOKEN_EXPIRED)
        } catch (e: Exception) {
            throw TokenException(TokenErrorCode.TOKEN_NOT_VALID)
        }
    }
}