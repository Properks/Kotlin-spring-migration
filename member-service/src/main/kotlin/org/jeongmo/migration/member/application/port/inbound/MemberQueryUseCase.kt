package org.jeongmo.migration.member.application.port.inbound

import org.jeongmo.migration.member.application.dto.MemberInfoResponse

interface MemberQueryUseCase {
    /**
     * 사용자 조회 메서드
     * @param id 사용자의 ID
     * @return 사용자의 데이터
     * @throws MemberException 사용자를 찾지 못한 경우 발생
     */
    fun findById(id: Long): MemberInfoResponse
}