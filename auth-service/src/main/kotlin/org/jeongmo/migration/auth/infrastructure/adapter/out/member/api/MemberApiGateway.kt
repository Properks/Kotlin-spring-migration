package org.jeongmo.migration.auth.infrastructure.adapter.out.member.api

import org.jeongmo.migration.auth.application.port.out.member.MemberServiceClient
import org.jeongmo.migration.auth.application.port.out.member.dto.*
import org.springframework.stereotype.Component

@Component
class MemberApiGateway: MemberServiceClient {

    override fun createMember(request: CreateMemberRequest): CreateMemberResponse {
        TODO("Not yet implemented")
    }

    override fun verifyMember(id: VerifyMemberRequest): VerifyMemberResponse? {
        TODO("Not yet implemented")
    }

}