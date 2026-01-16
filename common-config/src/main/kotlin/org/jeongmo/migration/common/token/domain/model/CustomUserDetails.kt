package org.jeongmo.migration.common.token.domain.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val id: Long,
    private val username: String,
    private val password: String,
    private val roles: List<String>,
): UserDetails {
    override fun getUsername(): String = this.username
    override fun getPassword(): String = this.password
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableSet()
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}