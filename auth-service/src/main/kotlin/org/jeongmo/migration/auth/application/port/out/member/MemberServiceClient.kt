package org.jeongmo.migration.auth.application.port.out.member

import org.jeongmo.migration.auth.application.port.out.member.dto.*

interface MemberServiceClient {
    fun createMember(request: CreateMemberRequest): CreateMemberResponse?
    fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse?
}