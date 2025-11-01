package org.jeongmo.migration.auth.application.error.code

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): BaseErrorCode {

    UNAUTHORIZED_DATA(HttpStatus.UNAUTHORIZED, "AUTH_401_1", "인증에 실패했습니다."),
    ;

    override fun getReason(): ErrorReasonDTO {
        return DefaultResponseErrorReasonDTO.builder()
            .httpStatus(this.httpStatus)
            .code(this.code)
            .message(this.message)
            .build()
    }

}