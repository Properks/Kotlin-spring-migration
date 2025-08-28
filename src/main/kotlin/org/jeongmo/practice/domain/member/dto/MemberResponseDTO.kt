package org.jeongmo.practice.domain.member.dto

import org.jeongmo.practice.domain.member.entity.enums.ProviderType
import org.jeongmo.practice.domain.member.entity.enums.Role
import java.time.LocalDateTime

data class FindMemberInfoResponse(val username: String, val nickname: String, val providerType: ProviderType, val role: Role, val createdAt: LocalDateTime)
data class UpdateMemberInfoResponse(val username: String, val nickname: String, val updatedAt: LocalDateTime)