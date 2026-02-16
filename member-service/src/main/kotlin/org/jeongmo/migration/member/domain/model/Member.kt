package org.jeongmo.migration.member.domain.model

import org.jeongmo.migration.common.domain.base.BaseDomain
import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.common.enums.member.Role
import org.jeongmo.migration.member.application.error.code.MemberErrorCode
import org.jeongmo.migration.member.application.error.exception.MemberException
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

    /**
     * @return true: it changed, false: nickname parameter is the same as field
     */
    fun changeNickname(nickname: String): Boolean {
        if (this.nickname == nickname) {
            return false
        }
        this.nickname = nickname
        return true
    }

    /**
     * @return 변경되었으면 true, 이미 같은 값이면 false
     */
    fun changeRole(role: Role): Boolean {
        return if (this.role == role) false else {
            this.role = role
            true
        }
    }
}