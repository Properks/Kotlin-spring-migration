package org.jeongmo.migration.member.application.port.inbound

import org.jeongmo.migration.member.application.dto.*

interface MemberCommandUseCase {
    fun createMember(request: CreateMemberRequest): CreateMemberResponse
    fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse?
    fun updateMemberInfos(id: Long, request: UpdateMemberInfoRequest): UpdateMemberInfoResponse
    fun deleteMember(id: Long)
}