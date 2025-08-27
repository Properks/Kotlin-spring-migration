package org.jeongmo.practice.global.security.auth.converter

import org.jeongmo.practice.global.security.auth.dto.AuthRequestDTO
import org.jeongmo.practice.domain.member.entity.Member
import org.jeongmo.practice.domain.member.entity.enums.ProviderType
import org.jeongmo.practice.domain.member.entity.enums.Role
import org.jeongmo.practice.global.security.auth.dto.AuthResponseDTO
import org.springframework.security.crypto.password.PasswordEncoder

object AuthConverter {
    fun AuthRequestDTO.SignUp.toEntity(passwordEncoder: PasswordEncoder): Member {
        return Member(
            username = this.username,
            password = passwordEncoder.encode(this.password),
            providerType = ProviderType.LOCAL,
            nickname = this.nickname,
            role = Role.USER,
            deletedAt = null
        )
    }

}
