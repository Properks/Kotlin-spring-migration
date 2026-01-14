package org.jeongmo.migration.bought.item.application.port.out.item

import org.jeongmo.migration.bought.item.application.port.out.item.dto.ItemInfoResponse

interface ItemServiceClient {

    /**
     * 상품 데이터 조회 메서드
     * @param itemId 상품 Id
     * @return 상품 데이터
     * @throws BoughtItemException 상품을 찾지 못한 경우
     */
    fun getItem(itemId: Long): ItemInfoResponse

    /**
     * 상품 개수 감소 메서드
     * @param ownerId 구매 사용자 id
     * @param itemId 상품 Id
     * @param quantity 감소할 상품 개수
     * @throws BoughtItemException 상품 개수 감소에 실패한 경우
     */
    fun decreaseItemCount(ownerId: Long, itemId: Long, quantity: Long)

    /**
     * 상품 개수 증가 메서드
     * @param ownerId 구매 사용자 id
     * @param itemId 상품 Id
     * @param quantity 추가할 상품 개수
     * @throws BoughtItemException 상품 개수 증가에 실패한 경우
     */
    fun increaseItemCount(ownerId: Long, itemId: Long, quantity: Long)
}