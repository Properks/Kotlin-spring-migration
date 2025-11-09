package org.jeongmo.migration.item.application.error.code

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.springframework.http.HttpStatus

enum class ItemErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): BaseErrorCode {

    ;

    override fun getReason(): ErrorReasonDTO =
        DefaultResponseErrorReasonDTO.builder()
            .httpStatus(this.httpStatus)
            .code(this.code)
            .message(this.message)
            .build()
}