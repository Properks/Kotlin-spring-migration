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
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "BOUGHT_ITEM_404_2", "해당 아이템을 찾을 수 없습니다."),
    FAIL_TO_DECREASE_ITEM_COUNT(HttpStatus.INTERNAL_SERVER_ERROR, "BOUGHT_ITEM_500_1", "아이템 수량 감소에 실패했습니다."),
    FAIL_TO_INCREASE_ITEM_COUNT(HttpStatus.INTERNAL_SERVER_ERROR, "BOUGHT_ITEM_500_1", "아이템 수량 복구₩에 실패했습니다."),
    INVALID_DATA(HttpStatus.BAD_REQUEST, "BOUGHT_ITEM_400_3", "구매한 상품의 데이터 형식이 유효하지 않습니다."),
    OPTIMISTIC_LOCK_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BOUGHT_ITEM_500_2", "낙관적 락 충돌로 인한 재시도 횟수를 초과하였습니다.")
    ;

    override fun getReason(): ErrorReasonDTO =
        DefaultResponseErrorReasonDTO.builder()
            .httpStatus(this.httpStatus)
            .code(this.code)
            .message(this.message)
            .build()
}