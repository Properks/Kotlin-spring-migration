package org.jeongmo.migration.api.gateway.security.handler

import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.namul.api.payload.code.DefaultResponseErrorCode
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Order(-1)
class CustomErrorWebExceptionHandler(
    private val httpResponseUtil: HttpResponseUtil,
): ErrorWebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (exchange.response.isCommitted) {
            return Mono.error(ex)
        }
        return httpResponseUtil.writeResponse(exchange, DefaultResponseErrorCode._INTERNAL_SERVER_ERROR, ex.message)
    }
}