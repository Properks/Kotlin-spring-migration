package org.jeongmo.migration.api.gateway.security.handler

import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.code.supports.DefaultResponseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Order(-2)
@Component
class GatewayExceptionHandler(
    private val httpResponseUtil: HttpResponseUtil,
): ErrorWebExceptionHandler {

    private val log = LoggerFactory.getLogger(GatewayExceptionHandler::class.java)

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val internalServerErrorCode = DefaultResponseErrorCode.INTERNAL_SERVER_ERROR
        return when (ex) {
            is ServerApplicationException -> {
                log.warn("Server Error in API-GATEWAY: ${exchange.request.method} ${exchange.request.path}", ex)
                if (exchange.response.isCommitted) {
                    return Mono.error(ex)
                }
                val errorCode = ex.code as? DefaultBaseErrorCode ?: internalServerErrorCode
                httpResponseUtil.writeResponse(exchange, errorCode, ex)
            }
            else -> {
                log.error("Unhandled Server Error in API-GATEWAY: ${exchange.request.method} ${exchange.request.path}", ex)
                if (exchange.response.isCommitted) {
                    return Mono.error(ex)
                }
                httpResponseUtil.writeResponse(exchange, internalServerErrorCode, ServerApplicationException(
                    internalServerErrorCode, ex))
            }
        }
    }
}