package org.jeongmo.migration.api.gateway.security.port.out.auth

import org.jeongmo.migration.api.gateway.security.port.out.auth.dto.AuthorizeResponse
import org.namul.api.payload.response.supports.DefaultResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class AuthApiGateway(
    private val webClient: WebClient,
) {

    private val authorizeEndpoint: String = "/auth"
    val authorizeHeaderName = "Authorization"

    fun authorize(authorizeHeader: String): Mono<AuthorizeResponse> {
        val type = object : ParameterizedTypeReference<DefaultResponse<AuthorizeResponse>>() {}
        return webClient.post()
            .uri(authorizeEndpoint)
            .header(authorizeHeaderName, authorizeHeader)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(type)
            .flatMap { Mono.just(it.result) }
    }
}