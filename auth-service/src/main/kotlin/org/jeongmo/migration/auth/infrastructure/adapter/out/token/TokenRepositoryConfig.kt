package org.jeongmo.migration.auth.infrastructure.adapter.out.token

import org.jeongmo.migration.common.token.infrastructure.adapter.out.redis.TokenTTLRepository
import org.jeongmo.migration.common.utils.ttl.TTLRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenRepositoryConfig(
    @Value("\${token.jwt.expiration-time.access-token}") private val accessTokenExpiration: Long,
    @Value("\${token.jwt.expiration-time.refresh-token}") private val refreshTokenExpiration: Long,
) {

    @Bean
    fun tokenRepository(ttlRepository: TTLRepository) = TokenTTLRepository(ttlRepository, accessTokenExpiration, refreshTokenExpiration)
}