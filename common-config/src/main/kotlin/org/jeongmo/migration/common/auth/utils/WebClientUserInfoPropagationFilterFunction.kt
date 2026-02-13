package org.jeongmo.migration.common.auth.utils

import org.jeongmo.migration.common.auth.constants.INTERNAL_SERVER_AUTH_ID_NAME
import org.jeongmo.migration.common.auth.constants.INTERNAL_SERVER_AUTH_ROLE_NAME
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import reactor.core.publisher.Mono

class WebClientUserInfoPropagationFilterFunction: ExchangeFilterFunction {

    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> {
        val currentRequest = try {
            (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request
        } catch (e: Exception) {
            null
        }
        val builder = ClientRequest.from(request)
        currentRequest?.getHeader(INTERNAL_SERVER_AUTH_ID_NAME)?.let {
            builder.header(INTERNAL_SERVER_AUTH_ID_NAME, it)
        }
        currentRequest?.getHeader(INTERNAL_SERVER_AUTH_ROLE_NAME)?.let {
            builder.header(INTERNAL_SERVER_AUTH_ROLE_NAME, it)
        }

        return next.exchange(builder.build())
    }
}