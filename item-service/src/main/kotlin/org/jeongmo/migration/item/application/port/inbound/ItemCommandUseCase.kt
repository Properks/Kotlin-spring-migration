package org.jeongmo.migration.item.application.port.inbound

import org.jeongmo.migration.item.application.dto.*

interface ItemCommandUseCase {

    /**
     * 상품 생성 메서드
     * @param request 상품 생성에 필요한 RequestBody
     * @return 생성된 상품의 데이터
     */
    fun createItem(request: CreateItemRequest): CreateItemResponse

    /**
     * 상품 개수 감소 메서드
     * @param id 상품 id
     * @param request 상품 개수 감소에 필요한 RequestBody
     * @throws ItemException 상품을 찾지 못한 경우
     */
    fun decreaseItemCount(id: Long, request: DecreaseItemStockRequest)

    /**
     * 상품 개수 증가 메서드
     * @param id 상품 id
     * @param request 상품 개수 증가에 필요한 RequestBody
     * @throws ItemException 상품을 찾지 못한 경우
     */
    fun increaseItemCount(id: Long, request: IncreaseItemStockRequest)

    /**
     * 상품 수정 메서드
     * @param id 상품 id
     * @param request 상품 수정에 필요한 RequestBody
     * @return 수정된 상품 데이터
     *
     */
    fun updateItem(id: Long, request: UpdateItemRequest): UpdateItemResponse

    /**
     * 상품 삭제 메서드
     * @param id 상품 id
     * @throws ItemException 상품을 찾지 못하거나 이미 삭제된 경우
     */
    fun deleteItem(id: Long)
}