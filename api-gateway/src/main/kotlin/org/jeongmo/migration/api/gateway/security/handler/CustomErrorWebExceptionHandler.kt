package org.jeongmo.migration.api.gateway.security.handler

import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.namul.api.payload.code.DefaultResponseErrorCode
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(CustomErrorWebExceptionHandler::class.java)
    private val clientMessage = "Internal Server Error"

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        logger.warn("Unhandled Server Error in API-GATEWAY", ex)
        if (exchange.response.isCommitted) {
            return Mono.error(ex)
        }
        return httpResponseUtil.writeResponse(exchange, DefaultResponseErrorCode._INTERNAL_SERVER_ERROR, clientMessage)
    }
}