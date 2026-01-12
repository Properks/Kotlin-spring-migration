package org.jeongmo.migration.auth.domain.repository

interface TokenRepository {

    /**
     * 토큰 저장 메서드
     * @param token 저장할 토큰
     * @param ttl 토큰 저장 유효기간 (ms)
     * @return 저장된 경우 true, 저장하지 못한 경우 false
     */
    fun saveToken(token: String, ttl: Long): Boolean

    /**
     * 저장소에서 토큰 제거 메서드
     * @param token 제거할 토큰
     * @return 제거에 성공했으면 true, 없는 데이터이거나 실패한 경우 false
     */
    fun removeToken(token: String): Boolean

    /**
     * 특정 토큰을 블랙 리스트에 넣어 제외하기 위한 메서드
     * @param token 블랙 리스트에 저장할 토큰
     * @param ttl 블랙 리스트를 유지할 시간 (ms)
     * @return 블랙 리스트 처리에 성공한 경우 true, 실패한 경우 false
     */
    fun blacklistToken(token: String, ttl: Long): Boolean

}