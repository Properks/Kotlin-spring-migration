package org.jeongmo.migration.bought.item.application.port.inbound

import org.jeongmo.migration.bought.item.application.dto.FindBoughtItemResponse
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException

interface BoughtItemQueryUseCase {

    /**
     * 상품 단일 조회 메서드
     * @param ownerId 구매 사용자 id
     * @param boughtItemId 구매 상품 id
     * @return 구매 상품 데이터
     * @throws BoughtItemException 상품을 찾지 못한 경우
     */
    fun findById(ownerId: Long, boughtItemId: Long): FindBoughtItemResponse

    /**
     * 구매 상품 전체 조회
     * @param ownerId 구매 사용자 Id
     * @return 구매 상품 데이터 List
     */
    fun findAll(ownerId: Long): List<FindBoughtItemResponse>
}