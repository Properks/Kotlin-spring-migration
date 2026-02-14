package org.jeongmo.migration.common.auth.domain

import org.jeongmo.migration.common.enums.member.Role
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.util.Assert

class InternalServiceAuthenticationToken: AbstractAuthenticationToken(listOf(SimpleGrantedAuthority("ROLE_${Role.INTERNAL_SERVICE.name}"))) {

    init {
        super.setAuthenticated(true)
    }

    override fun getPrincipal(): Any = "internal-service"
    override fun getCredentials(): Any? = null
    override fun setAuthenticated(authenticated: Boolean) {
        Assert.isTrue(!authenticated, "해당 토큰에서 setAuthenticated(true) 기능을 사용할 수 없습니다. 생성자를 이용해주세요.")
        super.setAuthenticated(false)
    }
}