package org.jeongmo.migration.common.token.application.error.code

import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.springframework.http.HttpStatus

enum class TokenErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): DefaultBaseErrorCode {

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_401_1", "토큰의 유효기간이 만료되었습니다."),
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "TOKEN_401_2", "토큰 인증에 실패했습니다."),
    FAIL_READ_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_401_3", "토큰 읽기에 실패했습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "TOKEN_401_4", "토큰 타입이 유효하지 않습니다."),
    CANNOT_REISSUE(HttpStatus.UNAUTHORIZED, "TOKEN_401_5", "해당 토큰으로 재발급할 수 없습니다."),
    BLACK_LIST_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN_400_1", "사용할 수 없는 토큰입니다."),
    ;

    override fun getHttpStatus(): HttpStatus = this.httpStatus
    override fun getCode(): String = this.code
    override fun getMessage(): String = this.message
}