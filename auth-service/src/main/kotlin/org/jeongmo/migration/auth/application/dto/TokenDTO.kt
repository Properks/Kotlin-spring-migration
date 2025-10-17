package org.jeongmo.migration.auth.application.dto

data class TokenInfoDTO(val id: Long, val username: String, val roles: Collection<Any>)
