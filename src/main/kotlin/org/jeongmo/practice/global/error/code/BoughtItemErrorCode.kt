package org.jeongmo.practice.global.error.code

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.springframework.http.HttpStatus

enum class BoughtItemErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "BOUGHT_ITEM404_1", "구매한 상품을 찾지 못했습니다."),
    NOT_YOURS(HttpStatus.BAD_REQUEST, "BOUGHT_ITEM400_1", "본인이 구매한 상품이 아닙니다."),
    ;

    override fun getReason(): ErrorReasonDTO {
        return DefaultResponseErrorReasonDTO.builder()
            .httpStatus(this.httpStatus)
            .code(this.code)
            .message(this.message)
            .build()
    }
}