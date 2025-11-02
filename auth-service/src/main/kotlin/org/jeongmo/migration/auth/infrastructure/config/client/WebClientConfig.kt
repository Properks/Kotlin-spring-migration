package org.jeongmo.migration.auth.infrastructure.config.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${web-client.base-url.member}") private val baseUrl: String,
) {

    @Bean
    @LoadBalanced
    fun loadBalancedWebClientBuilder(): WebClient.Builder = WebClient.builder()

    @Bean
    fun memberWebClient(loadBalancerClientBuilder: WebClient.Builder): WebClient =
        loadBalancerClientBuilder
            .baseUrl(baseUrl)
            .build()
}