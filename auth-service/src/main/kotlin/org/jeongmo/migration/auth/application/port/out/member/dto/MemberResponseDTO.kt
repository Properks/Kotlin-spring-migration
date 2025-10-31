package org.jeongmo.migration.auth.application.port.out.member.dto

import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.common.enums.member.Role
import java.time.LocalDateTime

data class CreateMemberResponse(
    val id: Long,
    val createdAt: LocalDateTime?,
)

data class MemberInfoResponse(
    val id: Long,
    val username: String,
    val nickname: String,
    val providerType: ProviderType,
    val role: Role,
)


data class VerifyMemberResponse(
    val id: Long,
    val username: String,
    val nickname: String,
    val providerType: ProviderType,
    val role: Role,
)