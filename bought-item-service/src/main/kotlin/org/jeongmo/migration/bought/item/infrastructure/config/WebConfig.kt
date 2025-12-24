package org.jeongmo.migration.bought.item.infrastructure.config

import org.jeongmo.migration.common.auth.annotation.resolver.LoginUserIdResolver
import org.jeongmo.migration.common.utils.idempotency.IdempotencyKeyGenerator
import org.jeongmo.migration.common.utils.idempotency.RequestAttributeIdempotencyKeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userIdResolver())
    }

    @Bean
    fun userIdResolver(): HandlerMethodArgumentResolver {
        return LoginUserIdResolver()
    }

    @Bean
    fun idempotencyKeyGenerator(): IdempotencyKeyGenerator = RequestAttributeIdempotencyKeyGenerator()
}