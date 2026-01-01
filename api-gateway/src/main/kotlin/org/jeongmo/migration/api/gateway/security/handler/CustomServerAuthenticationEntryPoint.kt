package org.jeongmo.migration.api.gateway.security.handler

import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.namul.api.payload.code.supports.DefaultResponseErrorCode
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class CustomServerAuthenticationEntryPoint(
    private val httpResponseUtil: HttpResponseUtil,
): ServerAuthenticationEntryPoint {

    private val logger = LoggerFactory.getLogger(CustomServerAuthenticationEntryPoint::class.java)

    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException?): Mono<Void> {
        logger.warn("인증 실패 ${ex?.message}", ex)
        return httpResponseUtil.writeResponse(exchange, DefaultResponseErrorCode.UNAUTHORIZED, ex)
    }
}