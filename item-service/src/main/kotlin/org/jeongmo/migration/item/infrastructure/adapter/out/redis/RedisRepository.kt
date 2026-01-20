package org.jeongmo.migration.item.infrastructure.adapter.out.redis

import org.jeongmo.migration.common.utils.ttl.TTLRepository
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
): TTLRepository {

    private val log = LoggerFactory.getLogger(RedisRepository::class.java)

    override fun save(key: String, value: Any, ttl: Long): Boolean {
        try {
            redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttl))
            return true
        } catch (e: Exception) {
            log.warn("[FAIL_TO_SAVE_IN_TTL_REPOSITORY] RedisRepository | ${e.message}")
            return false
        }
    }

    override fun saveIfAbsent(key: String, value: Any, ttl: Long): Any? {
        return if (redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofSeconds(ttl)) == false) {
            redisTemplate.opsForValue()[key]
        }
        else null

    }

    override fun has(key: String): Boolean {
        return redisTemplate.hasKey(key)
    }

    override fun <T> findByKey(key: String, clz: Class<T>): T? {
        return redisTemplate.opsForValue()[key]?.let {
            try {
                clz.cast(it)
            } catch (e: Exception) {
                return null
            }
        }
    }

    override fun deleteValue(key: String): Boolean {
        return redisTemplate.delete(key)
    }
}