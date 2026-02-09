package org.jeongmo.migration.auth.infrastructure.config.web

import org.jeongmo.migration.common.auth.annotation.resolver.LoginUserIdResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(LoginUserIdResolver())
    }

    @Bean
    fun loginUserIdResolver(): HandlerMethodArgumentResolver = LoginUserIdResolver()
}