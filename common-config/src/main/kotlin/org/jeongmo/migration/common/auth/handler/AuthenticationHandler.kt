package org.jeongmo.migration.common.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.migration.common.utils.api.payload.HttpServletErrorResponseWriter
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.code.supports.DefaultResponseErrorCode
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class AuthenticationHandler(
    private val httpServletErrorResponseWriter: HttpServletErrorResponseWriter<DefaultBaseErrorCode>,
): AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val code = DefaultResponseErrorCode.UNAUTHORIZED
        httpServletErrorResponseWriter.writeResponse(response, code, authException)
    }
}