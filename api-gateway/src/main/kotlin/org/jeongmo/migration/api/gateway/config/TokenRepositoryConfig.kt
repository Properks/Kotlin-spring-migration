package org.jeongmo.migration.api.gateway.config

import org.jeongmo.migration.common.token.domain.repository.ReactiveTokenRepository
import org.jeongmo.migration.common.token.infrastructure.adapter.out.redis.TokenReactiveTTLRepository
import org.jeongmo.migration.common.utils.ttl.ReactiveTTLRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenRepositoryConfig(
    @Value("\${token.jwt.expiration-time.access-token}") private val accessTokenExpiration: Long,
    @Value("\${token.jwt.expiration-time.refresh-token}") private val refreshTokenExpiration: Long,
) {

    @Bean
    fun reactiveTokenRepository(reactiveTTLRepository: ReactiveTTLRepository): ReactiveTokenRepository = TokenReactiveTTLRepository(reactiveTTLRepository, accessTokenExpiration, refreshTokenExpiration)
}