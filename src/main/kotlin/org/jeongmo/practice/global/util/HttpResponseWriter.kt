package org.jeongmo.practice.global.util

import jakarta.servlet.http.HttpServletResponse
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.SuccessReasonDTO
import org.springframework.http.HttpStatus

interface HttpResponseWriter<S: SuccessReasonDTO, E: ErrorReasonDTO> {
    fun writeSuccessResponse(httpServletResponse: HttpServletResponse, httpStatus: HttpStatus, reasonDTO: E, result: Any?)
    fun writeSuccessResponse(httpServletResponse: HttpServletResponse, httpStatus: HttpStatus, reasonDTO: S, result: Any?)
}