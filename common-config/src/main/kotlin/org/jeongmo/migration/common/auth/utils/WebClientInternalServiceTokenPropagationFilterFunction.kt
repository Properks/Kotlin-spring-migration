package org.jeongmo.migration.common.auth.utils

import org.jeongmo.migration.common.auth.constants.INTERNAL_SERVER_TOKEN_NAME
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import reactor.core.publisher.Mono

class WebClientInternalServiceTokenPropagationFilterFunction(
    private val authenticationToken: String,
): ExchangeFilterFunction {

    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> {
        val builder = ClientRequest.from(request)
        return next.exchange(builder
            .header(INTERNAL_SERVER_TOKEN_NAME, authenticationToken)
            .build()
        )
    }
}