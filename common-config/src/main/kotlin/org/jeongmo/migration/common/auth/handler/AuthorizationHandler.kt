package org.jeongmo.migration.common.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.migration.common.utils.api.payload.HttpServletErrorResponseWriter
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.code.supports.DefaultResponseErrorCode
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class AuthorizationHandler(
    private val httpServletErrorResponseWriter: HttpServletErrorResponseWriter<DefaultBaseErrorCode>,
): AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        val code = DefaultResponseErrorCode.FORBIDDEN
        httpServletErrorResponseWriter.writeResponse(response, code, accessDeniedException)
    }
}