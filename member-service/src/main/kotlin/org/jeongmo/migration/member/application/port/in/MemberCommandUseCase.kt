package org.jeongmo.migration.member.application.port.`in`

import org.jeongmo.migration.member.application.dto.CreateMemberRequest
import org.jeongmo.migration.member.application.dto.CreateMemberResponse
import org.jeongmo.migration.member.application.dto.VerifyMemberRequest
import org.jeongmo.migration.member.application.dto.VerifyMemberResponse

interface MemberCommandUseCase {
    fun createMember(request: CreateMemberRequest): CreateMemberResponse
    fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse?
}