package org.jeongmo.practice.global.data

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtConfigData (
    val secret : String,
    val time : JwtExpiration
) {
}

class JwtExpiration(val accessToken: Long, val refreshToken : Long)