package org.jeongmo.migration.member.application.port.inbound

import org.jeongmo.migration.member.application.dto.*
import org.jeongmo.migration.member.application.error.exception.MemberException

interface MemberCommandUseCase {
    /**
     * 사용자 생성 메서드
     * @param request 사용자 생성에 필요한 RequestBody
     * @return 생성된 사용자의 데이터
     * @throws MemberException 사용자의 데이터가 유효하지 않은 경우 발생
     */
    fun createMember(request: CreateMemberRequest): CreateMemberResponse

    /**
     * 유효한 사용자인지 확인하는 메서드
     * @param request 유효한 사용자인지 확인하기 위해 필요한 RequestBody
     * @return 유효한 사용자인 경우 사용자의 데이터 반환
     * @throws MemberException 사용자를 찾지 못하거나 비밀번호가 유효하지 않은 경우 발생
     */
    fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse

    /**
     * 사용자의 정보를 수정하는 메서드
     * @param id 수정할 사용자의 ID
     * @param request 수정할 데이터
     * @return 수정된 사용자의 데이터와 수정 시간
     * @throws MemberException 사용자를 찾지 못한 경우 발생
     */
    fun updateMemberInfos(id: Long, request: UpdateMemberInfoRequest): UpdateMemberInfoResponse

    /**
     * 사용자 역할을 수정하는 메서드
     * @param request 사용자 권한 변경에 필요한 요청
     */
    fun updateMemberRole(request: UpdateMemberRoleRequest): UpdateMemberRoleResponse

    /**
     * 사용자 삭제 메서드
     * @param id 삭제할 사용자의 ID
     * @throws MemberException 이미 삭제된 사용자의 경우 에러 발생
     */
    fun deleteMember(id: Long)
}