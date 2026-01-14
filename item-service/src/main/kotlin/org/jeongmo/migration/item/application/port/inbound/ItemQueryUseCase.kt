package org.jeongmo.migration.item.application.port.inbound

import org.jeongmo.migration.item.application.dto.ItemInfoResponse

interface ItemQueryUseCase {

    /**
     * 상품 단일 조회 메서드
     * @param id 상품 id
     * @return 상품 데이터
     * @throws ItemException 상품을 찾지 못한 경우
     */
    fun findById(id: Long): ItemInfoResponse

    /**
     * 상품 전체 조회 메서드
     * @return 상품 데이터 List
     */
    fun findAll(): List<ItemInfoResponse>
}