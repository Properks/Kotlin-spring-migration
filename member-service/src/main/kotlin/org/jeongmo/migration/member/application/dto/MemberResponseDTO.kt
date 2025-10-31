package org.jeongmo.migration.member.application.dto

import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.common.enums.member.Role
import org.jeongmo.migration.member.domain.model.Member
import java.time.LocalDateTime

data class CreateMemberResponse(
    val id: Long,
    val createdAt: LocalDateTime?,
) {
    companion object {
        fun fromDomain(member: Member) =
            CreateMemberResponse(
                id = member.id,
                createdAt = member.createdAt,
            )
    }
}

data class MemberInfoResponse(
    val id: Long,
    val username: String,
    val nickname: String,
    val providerType: ProviderType,
    val role: Role,
) {
    companion object {
        fun fromDomain(member: Member) =
            MemberInfoResponse(
                id = member.id,
                username = member.username,
                nickname = member.nickname,
                providerType = member.providerType,
                role = member.role,
            )
    }
}

data class VerifyMemberResponse(
    val id: Long,
    val username: String,
    val nickname: String,
    val providerType: ProviderType,
    val role: Role,
) {
    companion object {
        fun fromDomain(member: Member) =
            VerifyMemberResponse(
                id = member.id,
                username = member.username,
                nickname = member.nickname,
                providerType = member.providerType,
                role = member.role,
            )
    }
}