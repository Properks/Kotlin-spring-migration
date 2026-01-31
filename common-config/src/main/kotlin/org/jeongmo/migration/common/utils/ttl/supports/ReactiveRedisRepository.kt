package org.jeongmo.migration.common.utils.ttl.supports

import org.jeongmo.migration.common.utils.ttl.ReactiveTTLRepository
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import reactor.core.publisher.Mono
import java.time.Duration

class ReactiveRedisRepository(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Any?>
): ReactiveTTLRepository {

    private val log = LoggerFactory.getLogger(RedisRepository::class.java)

    override fun save(key: String, value: Any, ttl: Long): Mono<Boolean> {
        return Mono.just(try {
            reactiveRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttl))
            true
        } catch (e: Exception) {
            log.warn("[FAIL_TO_SAVE_IN_TTL_REPOSITORY] RedisRepository | ${e.message}")
            false
        })
    }

    override fun saveIfAbsent(key: String, value: Any, ttl: Long): Mono<Any?> {
        return reactiveRedisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofSeconds(ttl)).flatMap {
            if (!it) {
                Mono.just(reactiveRedisTemplate.opsForValue()[key])
            }
            else {
                Mono.empty()
            }
        }
    }

    override fun has(key: String): Mono<Boolean> {
        return reactiveRedisTemplate.hasKey(key)
    }

    override fun <T> findByKey(key: String, clz: Class<T>): Mono<T?> {
        return reactiveRedisTemplate.opsForValue()[key].flatMap {
            try {
                val data = clz.cast(it)
                Mono.justOrEmpty(data)
            } catch (e: Exception) {
                Mono.empty()
            }
        }
    }


    override fun deleteValue(key: String): Mono<Boolean> {
        return reactiveRedisTemplate.delete(key).flatMap {
            if (it <= 0) {
                Mono.just(false)
            } else {
                Mono.just(true)
            }
        }
    }
}