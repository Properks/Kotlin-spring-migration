package org.jeongmo.practice.global.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.namul.api.payload.code.DefaultResponseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class CustomAccessDeniedHandler(
    private val filterExceptionHandler: FilterExceptionHandler,
): AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        filterExceptionHandler.handleServerApplicationException(
            request = request!!,
            response = response!!,
            exception = ServerApplicationException(DefaultResponseErrorCode._FORBIDDEN),
        )
    }
}