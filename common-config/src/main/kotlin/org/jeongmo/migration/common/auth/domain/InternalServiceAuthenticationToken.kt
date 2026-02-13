package org.jeongmo.migration.common.auth.domain

import org.jeongmo.migration.common.enums.member.Role
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

class InternalServiceAuthenticationToken: AbstractAuthenticationToken(listOf(SimpleGrantedAuthority("ROLE_${Role.INTERNAL_SERVICE.name}"))) {

    override fun getPrincipal(): Any = "internal-service"
    override fun getCredentials(): Any? = null
    override fun isAuthenticated(): Boolean = true
}