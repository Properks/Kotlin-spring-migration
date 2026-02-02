package org.jeongmo.migration.auth.application.error.code

import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): DefaultBaseErrorCode {

    UNAUTHORIZED_DATA(HttpStatus.UNAUTHORIZED, "AUTH_401_1", "인증에 실패했습니다."),
    FAIL_SIGN_UP(HttpStatus.BAD_REQUEST, "AUTH_400_1", "회원가입에 실패했습니다."),
    FAIL_TO_VERIFY(HttpStatus.UNAUTHORIZED, "AUTH_401_2", "로그인에 실패했습니다."),
    UNSUPPORTED_TYPE(HttpStatus.BAD_REQUEST, "AUTH_400_2", "잘못된 형식입니다.")
    ;

    override fun getHttpStatus(): HttpStatus = this.httpStatus
    override fun getCode(): String = this.code
    override fun getMessage(): String = this.message
}