package org.jeongmo.migration.auth.application.port.out.member

import org.jeongmo.migration.auth.application.port.out.member.dto.*

// 성공 시 데이터 반환 실패 시 에러 발생
interface MemberServiceClient {
    fun createMember(request: CreateMemberRequest): CreateMemberResponse
    fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse
}