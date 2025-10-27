package org.jeongmo.migration.common.token.application.error.code

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.springframework.http.HttpStatus

enum class TokenErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): BaseErrorCode {

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_401_1", "토큰의 유효기간이 만료되었습니다."),
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "TOKEN_401_2", "토큰 인증에 실패했습니다."),
    ;

    override fun getReason(): ErrorReasonDTO = DefaultResponseErrorReasonDTO.builder()
        .httpStatus(this.httpStatus)
        .code(this.code)
        .message(this.message)
        .build()
}