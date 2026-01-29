package org.jeongmo.migration.item.infrastructure.adapter.out.ttl

import org.jeongmo.migration.common.utils.ttl.supports.RedisRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class TTLRepositoryConfig {

    @Bean
    fun idempotencyTTLRepository(idempotencyKeyRedisTemplate: RedisTemplate<String, Any?>) = RedisRepository(idempotencyKeyRedisTemplate)
}