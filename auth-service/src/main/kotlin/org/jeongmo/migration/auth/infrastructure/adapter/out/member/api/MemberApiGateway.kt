package org.jeongmo.migration.auth.infrastructure.adapter.out.member.api

import org.jeongmo.migration.auth.application.port.out.member.MemberServiceClient
import org.jeongmo.migration.auth.application.port.out.member.dto.*
import org.jeongmo.migration.auth.application.port.out.member.enums.ProviderType
import org.jeongmo.migration.auth.application.port.out.member.enums.Role
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MemberApiGateway: MemberServiceClient {

    override fun createMember(request: CreateMemberRequest): CreateMemberResponse {
//        TODO("Not yet implemented")
        return CreateMemberResponse(
            id = 1L,
            createdAt = LocalDateTime.now(),
        )
    }

    override fun verifyMember(id: VerifyMemberRequest): VerifyMemberResponse? {
//        TODO("Not yet implemented")
        return VerifyMemberResponse(
            id = 1L,
            username = "username",
            nickname = "nickname",
            providerType = ProviderType.LOCAL,
            role = Role.USER,
        )
    }

}