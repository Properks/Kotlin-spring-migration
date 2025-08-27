package org.jeongmo.practice.global.security.token.service

import org.jeongmo.practice.global.data.JwtConfigData
import org.jeongmo.practice.global.util.RedisUtil
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisTokenStorageService(
    private val redisUtil: RedisUtil,
    jwtConfigData: JwtConfigData
): TokenStorageService {

    private val refreshPrefix: String = "REFRESH:"
    private val blackListPrefix: String= "BLACKLIST:"
    private val refreshTokenExpiration: Duration = Duration.ofMillis(jwtConfigData.time.refreshToken)

    override fun saveRefreshToken(username: String, token: String) = redisUtil.save("$refreshPrefix$username", token, refreshTokenExpiration)

    override fun getRefreshToken(username: String): String? = redisUtil.findByKey("$refreshPrefix$username", String::class.java)

    override fun saveBlackList(token: String) = redisUtil.save("$blackListPrefix$token", true, refreshTokenExpiration)

    override fun isBlackList(token: String): Boolean = redisUtil.hasKey("$blackListPrefix$token")

    override fun deleteBlackList(token: String) {
        redisUtil.deleteByKey("$blackListPrefix$token")
    }

}