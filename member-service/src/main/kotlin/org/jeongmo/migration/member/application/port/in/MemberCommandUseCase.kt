package org.jeongmo.migration.member.application.port.`in`

import org.jeongmo.migration.member.application.dto.CreateMemberRequest
import org.jeongmo.migration.member.application.dto.CreateMemberResponse

interface MemberCommandUseCase {
    fun createMember(request: CreateMemberRequest): CreateMemberResponse
}