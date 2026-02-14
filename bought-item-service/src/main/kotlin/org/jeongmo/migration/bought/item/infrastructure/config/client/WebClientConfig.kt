package org.jeongmo.migration.bought.item.infrastructure.config.client

import io.micrometer.observation.ObservationRegistry
import org.jeongmo.migration.common.auth.utils.WebClientUserInfoPropagationFilterFunction
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${web-client.base-url.item}") private val baseUrl: String,
    private val observationRegistry: ObservationRegistry,
) {

    @Bean
    @LoadBalanced
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder().observationRegistry(observationRegistry)
    }

    @Bean
    fun webClient(webclientBuilder: WebClient.Builder): WebClient {
        return webclientBuilder
            .baseUrl(baseUrl)
            .filter(webClientUserInfoPropagationFilterFunction())
            .build()
    }

    @Bean
    fun webClientUserInfoPropagationFilterFunction(): ExchangeFilterFunction = WebClientUserInfoPropagationFilterFunction()
}