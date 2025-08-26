package org.jeongmo.practice.global.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.namul.api.payload.code.DefaultResponseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class CustomAuthenticationEntryPoint(
    private val filterExceptionHandler: FilterExceptionHandler
): AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        filterExceptionHandler.handleServerApplicationException(
            request = request!!,
            response = response!!,
            exception = ServerApplicationException(DefaultResponseErrorCode._UNAUTHORIZED),
        )
    }
}