package org.jeongmo.migration.auth.application.dto

data class TokenInfoDTO(val id: String, val username: String, val roles: Collection<Any>)
