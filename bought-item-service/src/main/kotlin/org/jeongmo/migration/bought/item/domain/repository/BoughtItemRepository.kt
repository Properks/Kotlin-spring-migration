package org.jeongmo.migration.bought.item.domain.repository

import org.jeongmo.migration.bought.item.domain.model.BoughtItem

interface BoughtItemRepository {

    /**
     * 구매 상품 도메인 저장
     * @param boughtItem 저장할 구매 상품 도메인 데이터
     * @return 저장된 구매 상품 도메인 데이터
     */
    fun save(boughtItem: BoughtItem): BoughtItem

    /**
     * 구매 상품 단일 조회 메서드
     * @param ownerId 구매 사용자 id
     * @param id 구매 상품 id
     * @return 성공 시 구매 상품 데이터, 실패 시 null
     */
    fun findById(ownerId: Long, id: Long): BoughtItem?

    /**
     * 구매 상품 조회 메서드
     * @param ownerId 구매 사용자 id
     * @return 조회된 구매 상품 데이터 List
     */
    fun findAll(ownerId: Long): List<BoughtItem>
}