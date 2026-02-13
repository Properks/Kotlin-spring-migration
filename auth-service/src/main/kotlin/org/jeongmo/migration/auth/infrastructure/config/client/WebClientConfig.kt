package org.jeongmo.migration.auth.infrastructure.config.client

import io.micrometer.observation.ObservationRegistry
import org.jeongmo.migration.common.auth.utils.WebClientInternalServiceTokenPropagationFilterFunction
import org.jeongmo.migration.common.auth.utils.WebClientUserInfoPropagationFilterFunction
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${web-client.base-url.member}") private val baseUrl: String,
    @Value("\${internal-service.authentication.token}") private val token: String,
    private val observationRegistry: ObservationRegistry,
) {

    @Bean
    @LoadBalanced
    fun loadBalancedWebClientBuilder(): WebClient.Builder =
        WebClient.builder().observationRegistry(observationRegistry)

    @Bean
    fun memberWebClient(loadBalancerClientBuilder: WebClient.Builder): WebClient =
        loadBalancerClientBuilder
            .baseUrl(baseUrl)
            .filter(webClientUserInfoPropagationFilterFunction())
            .filter(webClientInternalServiceTokenPropagationFilterFunction())
            .build()

    @Bean
    fun webClientUserInfoPropagationFilterFunction(): ExchangeFilterFunction = WebClientUserInfoPropagationFilterFunction()

    @Bean
    fun webClientInternalServiceTokenPropagationFilterFunction(): ExchangeFilterFunction = WebClientInternalServiceTokenPropagationFilterFunction(token)
}