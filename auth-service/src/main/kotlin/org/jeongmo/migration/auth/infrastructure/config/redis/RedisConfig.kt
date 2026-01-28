package org.jeongmo.migration.auth.infrastructure.config.redis

import org.jeongmo.migration.common.token.infrastructure.adapter.out.redis.TokenTTLRepository
import org.jeongmo.migration.common.utils.ttl.TTLRepository
import org.jeongmo.migration.common.utils.ttl.supports.RedisRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    @Value("\${token.jwt.expiration-time.access-token}") private val accessTokenExpiration: Long,
) {

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any?> {
        val template: RedisTemplate<String, Any?> = RedisTemplate<String, Any?>()
        template.connectionFactory = connectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        return template
    }

    @Bean
    fun ttlRepository(redisTemplate: RedisTemplate<String, Any?>): TTLRepository = RedisRepository(redisTemplate)
    @Bean
    fun tokenRepository(ttlRepository: TTLRepository) = TokenTTLRepository(ttlRepository, accessTokenExpiration)
}