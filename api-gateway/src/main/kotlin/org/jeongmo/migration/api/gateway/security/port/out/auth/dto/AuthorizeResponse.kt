package org.jeongmo.migration.api.gateway.security.port.out.auth.dto

data class AuthorizeResponse(val id: Long, val roles: List<String>)