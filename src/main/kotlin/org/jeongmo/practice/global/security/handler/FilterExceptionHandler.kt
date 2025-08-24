package org.jeongmo.practice.global.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.namul.api.payload.error.exception.ServerApplicationException
import java.lang.Exception

interface FilterExceptionHandler {

    fun handleServerApplicationException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: ServerApplicationException,
    )

    fun handleException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: Exception,
    )
}