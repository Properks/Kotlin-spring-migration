package org.jeongmo.migration.api.gateway.config

import org.jeongmo.migration.common.token.domain.repository.TokenRepository
import org.jeongmo.migration.common.token.infrastructure.adapter.out.redis.TokenTTLRepository
import org.jeongmo.migration.common.utils.ttl.TTLRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenRepositoryConfig(
    @Value("\${token.jwt.expiration-time.access-token}") private val tokenExpirationTime: Long,
) {

    @Bean
    fun tokenRepository(ttlRepository: TTLRepository): TokenRepository = TokenTTLRepository(ttlRepository, tokenExpirationTime)
}