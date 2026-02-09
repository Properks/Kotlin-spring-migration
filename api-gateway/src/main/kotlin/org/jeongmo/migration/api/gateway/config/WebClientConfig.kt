package org.jeongmo.migration.api.gateway.config

import io.micrometer.observation.ObservationRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${web-client.base-url.auth}") private val baseUrl: String
) {

    @Bean
    @LoadBalanced
    fun webClientBuilder(observationRegistry: ObservationRegistry): WebClient.Builder {
        return WebClient.builder().observationRegistry(observationRegistry)
    }

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        return builder.baseUrl(baseUrl).build()
    }
}