package org.jeongmo.practice.global.error.code

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.springframework.http.HttpStatus

enum class TokenErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): BaseErrorCode {

    TOKEN_TIME_EXPIRED(HttpStatus.BAD_REQUEST, "TOKEN400_1", "토큰의 유효기간이 만료되었습니다.")
    ;

    override fun getReason(): DefaultResponseErrorReasonDTO {
        return DefaultResponseErrorReasonDTO.builder()
            .httpStatus(this.httpStatus)
            .code(this.code)
            .message(this.message)
            .build()
    }
}