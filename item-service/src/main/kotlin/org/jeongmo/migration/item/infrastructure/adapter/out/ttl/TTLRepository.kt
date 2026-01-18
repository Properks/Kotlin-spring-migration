package org.jeongmo.migration.item.infrastructure.adapter.out.ttl

/**
 * TTL을 이용하여 데이터를 관리하는 Repository
 *
 */
interface TTLRepository {

    /**
     * TTL 을 이용하여 값 저장, 수정 시 같은 Key 값에 ttl, value 설정하여 다시 저장
     *
     * @param key 저장할 때 사용할 Key 값, 데이터를 unique하게 식별하기 위해
     * @param value Key값에 매핑되는 데이터
     * @param ttl 해당 데이터를 유지할 시간 (sec)
     * @return 저장 성공 시 true, 실패 시 false
     */
    fun save(key: String, value: Any, ttl: Long): Boolean

    /**
     * 저장소가 해당 키를 가지고 있는지 확인
     *
     * @param key 확인할 키 값
     * @return 저장소가 해당 키 값에 대한 데이터를 가지고 있으면 true, 없으면 false
     */
    fun has(key: String): Boolean

    /**
     * Key를 이용하여 데이터를 찾을 때 이용
     *
     * @param T 반환 값 설정
     * @param key 찾을 데이터의 Key 값
     * @param clz 반환할 값의 타입을 설정
     * @return 찾은 데이터를 clz의 형태로 반환, 못 찾은 경우 null
     */
    fun <T> findByKey(key: String, clz: Class<T>): T?

    /**
     * 데이터 삭제 메서드
     *
     * @param key 삭제할 데이터의 Key값
     * @return 삭제 성공 시 true, 실패 시 false
     */
    fun deleteValue(key: String): Boolean

}