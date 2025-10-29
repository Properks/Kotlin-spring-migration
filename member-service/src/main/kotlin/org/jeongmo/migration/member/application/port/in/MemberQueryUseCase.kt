package org.jeongmo.migration.member.application.port.`in`

import org.jeongmo.migration.member.application.dto.FindMemberInfoRequest
import org.jeongmo.migration.member.application.dto.FoundMemberInfoResponse
import org.jeongmo.migration.member.application.dto.MemberInfoResponse

interface MemberQueryUseCase {
    fun findById(id: Long): MemberInfoResponse
    fun findByUsernameAndProviderType(request: FindMemberInfoRequest): FoundMemberInfoResponse
}