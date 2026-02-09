package org.jeongmo.migration.api.gateway.security.port.out.auth

import org.jeongmo.migration.api.gateway.security.port.out.auth.dto.AuthorizeResponse
import org.namul.api.payload.code.supports.DefaultResponseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException
import org.namul.api.payload.response.supports.DefaultResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class AuthApiGateway(
    private val webClient: WebClient,
) {

    private val authorizeEndpoint: String = "/auth"
    val authorizeHeaderName = "Authorization"

    /**
     * 인증 요청 메서드
     * @param authorizeHeader authorizeHeaderName으로 얻은 인증 헤더 값
     * @return 성공 시 인증된 정보, 실패 시 Mono.error() 반환
     */
    fun authorize(authorizeHeader: String): Mono<AuthorizeResponse> {
        val type = object : ParameterizedTypeReference<DefaultResponse<AuthorizeResponse>>() {}
        return webClient.post()
            .uri(authorizeEndpoint)
            .header(authorizeHeaderName, authorizeHeader)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(type)
            .timeout(Duration.ofSeconds(5))
            .flatMap {
                it.result?.let {result ->  Mono.just(result) }
                    ?: Mono.error(ServerApplicationException(DefaultResponseErrorCode.UNAUTHORIZED))
            }
    }
}