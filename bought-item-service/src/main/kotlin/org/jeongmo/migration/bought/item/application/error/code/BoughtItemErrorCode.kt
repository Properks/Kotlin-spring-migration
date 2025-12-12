package org.jeongmo.migration.bought.item.application.error.code

import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.springframework.http.HttpStatus

enum class BoughtItemErrorCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val message: String,
): BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "BOUGHT_ITEM_404_1", "구매한 상품을 찾을 수 없습니다."),
    ALREADY_SET(HttpStatus.BAD_REQUEST, "BOUGHT_ITEM_400_1", "이미 처리되었습니다."),
    ALREADY_DELETE(HttpStatus.BAD_REQUEST, "BOUGHT_ITEM_400_2", "이미 삭제되었습니다."),
    ;

    override fun getReason(): ErrorReasonDTO =
        DefaultResponseErrorReasonDTO.builder()
            .httpStatus(this.httpStatus)
            .code(this.code)
            .message(this.message)
            .build()
}