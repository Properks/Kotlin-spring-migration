package org.jeongmo.migration.item.infrastructure.config

import org.jeongmo.migration.common.auth.annotation.resolver.LoginUserIdResolver
import org.jeongmo.migration.common.utils.idempotency.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val idempotencyKeyRepository: IdempotencyKeyRepository,
): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserIdResolvers())
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(idempotencyInterceptor())
    }

    @Bean
    fun idempotencyInterceptor(): IdempotencyInterceptor =
        ScopeIdempotencyInterceptor(idempotencyKeyRepository = idempotencyKeyRepository)
            .addPattern(HttpMethod.PATCH, "/internal/api/items/**")

    @Bean
    fun idempotencyGenerator(): IdempotencyKeyGenerator = RequestAttributeIdempotencyKeyGenerator()

    @Bean
    fun loginUserIdResolvers(): HandlerMethodArgumentResolver = LoginUserIdResolver()
}