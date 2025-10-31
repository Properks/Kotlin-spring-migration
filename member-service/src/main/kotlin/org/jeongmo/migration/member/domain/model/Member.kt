package org.jeongmo.migration.member.domain.model

import org.jeongmo.migration.common.domain.base.BaseDomain
import org.jeongmo.migration.member.application.error.code.MemberErrorCode
import org.jeongmo.migration.member.application.error.exception.MemberException
import org.jeongmo.migration.member.domain.enum.ProviderType
import org.jeongmo.migration.member.domain.enum.Role
import java.time.LocalDateTime

class Member(
    val id: Long = 0L,
    var username: String,
    var password: String?,
    var providerType: ProviderType,
    var nickname: String,
    var role: Role,
    var deletedAt: LocalDateTime?,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
): BaseDomain(createdAt, updatedAt) {
    init {
        if (providerType == ProviderType.LOCAL && password == null) {
            throw MemberException(MemberErrorCode.INVALID_DATA)
        }
    }
}