package org.jeongmo.migration.common.token.application.dto

import org.jeongmo.migration.common.token.application.constants.TokenType
import org.springframework.security.core.GrantedAuthority

data class TokenInfoDTO(val id: String, val type: TokenType, val username: String, val roles: Collection<GrantedAuthority>)
