package org.jeongmo.migration.item.domain.repository

import org.jeongmo.migration.item.domain.model.Item

interface ItemRepository {

    /**
     * 상품 저장 메서드
     * @param item 저장할 상품의 도메인 데이터
     * @return 저장된 item 도메인 데이터
     */
    fun save(item: Item): Item

    /**
     * 상품 단일 조회 메서드
     * @param id 상품 id
     * @return 찾은 경우 상품 데이터, 못 찾은 경우 null
     */
    fun findById(id: Long): Item?

    /**
     * 상품 전체 조회 메서드
     * @return 상품 데이터 List
     */
    fun findAll(): List<Item>

    /**
     * 상품 제거 메서드
     * @param id 상품 id
     * @return 성공적으로 삭제한 경우 true, 삭제할 데이터가 없는 경우 false
     */
    fun deleteById(id: Long): Boolean
}