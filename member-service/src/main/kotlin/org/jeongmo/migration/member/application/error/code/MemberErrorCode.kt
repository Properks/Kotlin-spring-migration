package org.jeongmo.migration.member.application.error.code

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.springframework.http.HttpStatus

enum class MemberErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_404_1", "사용자를 찾지 못했습니다."),
    INVALID_DATA(HttpStatus.BAD_REQUEST, "MEMBER_400_1", "사용자 유형에 따른 데이터 양식이 맞지 않습니다.")
    ;

    override fun getReason(): ErrorReasonDTO =
        DefaultResponseErrorReasonDTO.builder()
            .httpStatus(this.httpStatus)
            .code(this.code)
            .message(this.message)
            .build()
}