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

    NOT_FOUND(HttpStatus.NOT_FOUND, "ITEM_404_1", "상품을 찾지 못했습니다."),
    ALREADY_DELETE(HttpStatus.BAD_REQUEST, "ITEM_400_1", "이미 삭제되었거나 존재하지 않는 상품입니다."),
    FAIL_ITEM_DELETE(HttpStatus.INTERNAL_SERVER_ERROR, "ITEM_500_1", "상품 삭제에 실패했습니다."),
    ;

    override fun getReason(): ErrorReasonDTO =
        DefaultResponseErrorReasonDTO.builder()
            .httpStatus(this.httpStatus)
            .code(this.code)
            .message(this.message)
            .build()
}