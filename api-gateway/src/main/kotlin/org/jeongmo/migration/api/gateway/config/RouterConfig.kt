package org.jeongmo.migration.api.gateway.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouterConfig {

    @Bean
    fun router(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator {
        return routeLocatorBuilder.routes()
            .route("auth-service") {
                it
                    .path("/auth/**")
                    .uri("lb://AUTH-SERVICE")
            }
            .route("member-service") {
                it
                    .path("/members/**")
                    .uri("lb://MEMBER-SERVICE")
            }
            .build()
    }

}