package org.jeongmo.migration.member.application.error.code

import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.springframework.http.HttpStatus

enum class MemberErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): DefaultBaseErrorCode{

    NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_404_1", "사용자를 찾지 못했습니다."),
    INVALID_DATA(HttpStatus.BAD_REQUEST, "MEMBER_400_1", "사용자 유형에 따른 데이터 양식이 맞지 않습니다."),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER_401_1", "비밀번호가 틀렸습니다."),
    ALREADY_DELETE(HttpStatus.BAD_REQUEST, "MEMBER_400_2", "이미 삭제된 사용자입니다."),
    ALREADY_UPDATE(HttpStatus.BAD_REQUEST,"MEMBER_400_3", "이미 변경되었습니다."),
    CANNOT_DELETE(HttpStatus.INTERNAL_SERVER_ERROR, "MEMBER_500_1", "사용자를 삭제할 수 없습니다."),
    ;

    override fun getHttpStatus(): HttpStatus = this.httpStatus
    override fun getCode(): String = this.code
    override fun getMessage(): String = this.message
}