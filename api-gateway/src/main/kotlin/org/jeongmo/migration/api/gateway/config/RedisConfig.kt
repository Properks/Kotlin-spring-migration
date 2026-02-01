package org.jeongmo.migration.api.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(redisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Any?> {
        val context = RedisSerializationContext
            .newSerializationContext<String, Any?>()
            .key(StringRedisSerializer())
            .value(GenericJackson2JsonRedisSerializer())
            .build()
        return ReactiveRedisTemplate<String, Any?>(redisConnectionFactory, context)
    }

}