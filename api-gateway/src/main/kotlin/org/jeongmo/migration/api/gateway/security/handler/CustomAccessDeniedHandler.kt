package org.jeongmo.migration.api.gateway.security.handler

import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.namul.api.payload.code.DefaultResponseErrorCode
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class CustomAccessDeniedHandler(
    private val httpResponseUtil: HttpResponseUtil,
): ServerAccessDeniedHandler {

    private val logger = LoggerFactory.getLogger(CustomAccessDeniedHandler::class.java)

    override fun handle(exchange: ServerWebExchange?, denied: AccessDeniedException?): Mono<Void> {
        logger.warn("인가 실패(권한 없음) ${denied?.message}", denied)
        return httpResponseUtil.writeResponse(exchange!!, DefaultResponseErrorCode._FORBIDDEN, denied?.message)
    }
}