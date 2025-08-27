package org.jeongmo.practice.global.util

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisUtil(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun save(key: String, value: Any, duration: Duration) = redisTemplate.opsForValue().set(key, value, duration)

    fun <T> findByKey(key: String, cls: Class<T>): T? = cls.cast(redisTemplate.opsForValue()[key])

    fun hasKey(key: String) = redisTemplate.hasKey(key)

    fun deleteByKey(key: String) = redisTemplate.delete(key)
}