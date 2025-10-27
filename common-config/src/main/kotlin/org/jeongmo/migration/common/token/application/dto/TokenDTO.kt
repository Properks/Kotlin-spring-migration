package org.jeongmo.migration.common.token.application.dto

import org.springframework.security.core.GrantedAuthority

data class TokenInfoDTO(val id: String, val username: String, val roles: Collection<GrantedAuthority>)
