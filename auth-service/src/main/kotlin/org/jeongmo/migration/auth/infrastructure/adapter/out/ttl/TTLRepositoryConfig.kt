package org.jeongmo.migration.auth.infrastructure.adapter.out.ttl

import org.jeongmo.migration.common.utils.ttl.TTLRepository
import org.jeongmo.migration.common.utils.ttl.supports.RedisRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class TTLRepositoryConfig {
    @Bean
    fun ttlRepository(redisTemplate: RedisTemplate<String, Any?>): TTLRepository = RedisRepository(redisTemplate)
}