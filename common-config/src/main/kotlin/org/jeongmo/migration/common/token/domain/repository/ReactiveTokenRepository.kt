package org.jeongmo.migration.common.token.domain.repository

import org.jeongmo.migration.common.token.application.constants.TokenType
import reactor.core.publisher.Mono

interface ReactiveTokenRepository {

    /**
     * 토큰 저장 메서드
     * @param id 사용자 id
     * @param token 저장할 토큰
     * @param type 저장하는 토큰의 형식
     * @return 저장된 경우 true, 저장하지 못한 경우 false
     */
    fun saveToken(id: Long, token: String,  type: TokenType): Mono<Boolean>

    /**
     * 토큰 검색 메서드
     * @param id 사용자 id
     * @param type 검색할 토큰 타입
     * @return 찾은 경우 토큰 값, 못 찾은 경우 null
     */
    fun getToken(id: Long, type: TokenType): Mono<String?>

    /**
     * 토큰이 블랙리스트에 들어갔는지 확인
     *
     * @param token 확인할 토큰
     * @return 블랙리스트면 true, 아니면 false
     */
    fun isBlackList(token: String): Mono<Boolean>

    /**
     * 저장소에서 토큰 제거 메서드
     * @param id 사용자 ID (REFRESH)
     * @param token 제거할 토큰 (BLACK_LIST)
     * @param type 제거할 토큰의 형식
     * @return 제거에 성공했으면 true, 없는 데이터이거나 실패한 경우 false
     */
    fun removeToken(id: Long?, token: String?, type: TokenType): Mono<Boolean>


}