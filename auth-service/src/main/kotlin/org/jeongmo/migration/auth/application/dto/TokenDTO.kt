package org.jeongmo.migration.auth.application.dto

import org.springframework.security.core.GrantedAuthority

data class TokenInfoDTO(val id: String, val username: String, val roles: Collection<GrantedAuthority>)
