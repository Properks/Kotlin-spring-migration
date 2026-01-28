package org.jeongmo.migration.item.infrastructure.config

import org.jeongmo.migration.common.utils.ttl.supports.RedisRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun idempotencyKeyRedisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any?> {
        val template: RedisTemplate<String, Any?> = RedisTemplate()
        template.connectionFactory = redisConnectionFactory
        template.keySerializer = RedisSerializer.string()
        template.valueSerializer = RedisSerializer.java()
        return template
    }

    @Bean
    fun ttlRepository(redisConnectionFactory: RedisConnectionFactory) = RedisRepository(idempotencyKeyRedisTemplate(redisConnectionFactory))
}