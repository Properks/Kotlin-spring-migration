package org.jeongmo.migration.member.application.dto

import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.common.enums.member.Role
import org.jeongmo.migration.member.domain.model.Member
import org.springframework.security.crypto.password.PasswordEncoder

data class CreateMemberRequest(
    val username: String,
    val password: String?,
    val providerType: ProviderType,
    val nickname: String,
    val role: Role,
) {
    fun toDomain(passwordEncoder: PasswordEncoder): Member =
        Member(
            username = this.username,
            password = password?.let {passwordEncoder.encode(it)},
            providerType = this.providerType,
            nickname = this.nickname,
            role = this.role,
            deletedAt = null,
        )
}

data class VerifyMemberRequest(
    val username: String,
    val password: String,
    val providerType: ProviderType,
)