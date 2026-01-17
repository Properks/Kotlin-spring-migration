package org.jeongmo.migration.bought.item.application.port.inbound

import org.jeongmo.migration.bought.item.application.dto.BuyItemRequest
import org.jeongmo.migration.bought.item.application.dto.BuyItemResponse
import org.jeongmo.migration.bought.item.application.dto.UpdateItemRequest
import org.jeongmo.migration.bought.item.application.dto.UpdateItemResponse
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException

interface BoughtItemCommandUseCase {

    /**
     * 상품 구매 메서드
     * @param ownerId 구매 사용자 id
     * @param request 상품 구매에 필요한 RequestBody
     * @return 구매한 상품 데이터
     * @throws BoughtItemException 재고 부족 등의 이유로 상품 구매에 실패한 경우
     */
    fun buyItem(ownerId: Long, request: BuyItemRequest): BuyItemResponse

    /**
     * 구매 상품 상태 수정 (관리자 권한)
     * @param boughtItemId 구매 상품 id
     * @param request 상품 상태 수정에 필요한 RequestBody
     * @return 수정된 상품 데이터
     */
    fun updateItemStatus(boughtItemId: Long, request: UpdateItemRequest): UpdateItemResponse

    /**
     * 상품 구매 취소
     * @param ownerId 구매 사용자 id
     * @param boughtItemId 구매 상품 id
     * @throws BoughtItemException 구매 상품을 찾지 못하거나 이미 취소된 경우
     */
    fun cancelBoughtItem(ownerId: Long, boughtItemId: Long)
}