package org.jeongmo.migration.api.gateway.config

import org.jeongmo.migration.common.utils.ttl.ReactiveTTLRepository
import org.jeongmo.migration.common.utils.ttl.supports.ReactiveRedisRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.ReactiveRedisTemplate

@Configuration
class TTLRepositoryConfig {

    @Bean
    fun ttlRepository(redisTemplate: ReactiveRedisTemplate<String, Any?>): ReactiveTTLRepository = ReactiveRedisRepository(redisTemplate)


}