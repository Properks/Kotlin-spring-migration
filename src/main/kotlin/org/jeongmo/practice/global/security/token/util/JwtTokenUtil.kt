package org.jeongmo.practice.global.security.token.util

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.jeongmo.practice.global.data.JwtConfigData
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenUtil(
    configData : JwtConfigData
): TokenUtil<Jws<Claims>> {

    val secretKey : SecretKey = Keys.hmacShaKeyFor(configData.secret.toByteArray())

    override fun createToken(memberInfo: UserDetails, expirationMillis: Long): String {
        val now : Instant = Instant.now()
        return Jwts.builder()
            .subject(memberInfo.username)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusMillis(expirationMillis)))
            .signWith(secretKey)
            .compact();
    }

    override fun getClaims(token: String): Jws<Claims> {
        return Jwts.parser()
            .verifyWith(secretKey)
            .clockSkewSeconds(60)
            .build()
            .parseSignedClaims(token)
    }
}