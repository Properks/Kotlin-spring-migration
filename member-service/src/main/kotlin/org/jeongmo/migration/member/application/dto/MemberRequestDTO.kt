package org.jeongmo.migration.member.application.dto

import org.jeongmo.migration.member.domain.enum.ProviderType
import org.jeongmo.migration.member.domain.enum.Role
import org.jeongmo.migration.member.domain.model.Member

data class CreateMemberRequest(
    val username: String,
    val encodedPassword: String?,
    val providerType: ProviderType,
    val nickname: String,
    val role: Role,
) {
    fun toDomain(): Member =
        Member(
            id = null,
            username = this.username,
            password = this.encodedPassword,
            providerType = this.providerType,
            nickname = this.nickname,
            role = this.role,
            deletedAt = null,
        )
}