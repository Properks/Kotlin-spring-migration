package org.jeongmo.practice.global.security.domain

import org.jeongmo.practice.domain.member.entity.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections

class CustomUserDetails(
    private val member: Member
): UserDetails {

    override fun getPassword(): String {
        return member.password ?: ""
    }

    override fun getUsername(): String {
        return member.username
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Collections.singleton(SimpleGrantedAuthority("ROLE_${member.role}"))
    }
}