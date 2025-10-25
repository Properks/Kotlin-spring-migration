package org.jeongmo.migration.member.domain.model

import org.jeongmo.migration.common.domain.base.BaseDomain
import org.jeongmo.migration.member.domain.enum.ProviderType
import org.jeongmo.migration.member.domain.enum.Role
import java.time.LocalDateTime

class Member(
    val id: Long?,
    var username: String,
    var password : String?,
    var providerType : ProviderType,
    var nickname : String,
    var role : Role,
    var deletedAt : LocalDateTime?,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
): BaseDomain(createdAt, updatedAt) {
}