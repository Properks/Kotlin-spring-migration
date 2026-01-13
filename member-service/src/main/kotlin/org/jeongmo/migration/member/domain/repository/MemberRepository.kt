package org.jeongmo.migration.member.domain.repository

import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.member.domain.model.Member

interface MemberRepository {

    /**
     * 사용자 저장 메서드
     * @param member 저장할 사용자의 도메인 데이터
     * @return 저장된 이후의 사용자 도메인 데이터
     */
    fun save(member: Member): Member

    /**
     * 사용자 조회 메서드
     * @param id 조회할 사용자의 ID
     * @return 성공적으로 조회한 경우 사용자, 조회에 실패한 경우 null
     */
    fun findById(id: Long): Member?

    /**
     * 사용자 이름과 로그인 방식으로 조회하는 메서드
     * @param username 사용자 이름
     * @param providerType 로그인 방식
     * @return 성공적으로 조회한 경우 사용자, 조회에 실패한 경우 null
     */
    fun findByUsernameAndProviderType(username: String, providerType: ProviderType): Member?

    /**
     * 사용자 전체 조회
     * @return 조회한 전체 사용자
     */
    fun findAll(): List<Member>

    /**
     * 사용자 삭제 메서드
     * @param id 삭제할 사용자 ID
     * @return 삭제한 경우 true, 이미 삭제되었거나 존재하지 않는 데이터인 경우 false
     */
    fun delete(id: Long): Boolean
}