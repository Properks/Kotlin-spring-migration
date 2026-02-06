package org.jeongmo.migration.auth.infrastructure.util

import org.springframework.stereotype.Component

@Component
class BearerTokenExtractor: HeaderTokenExtractor() {

    override fun getHeaderName(): String = "Authorization"

    override fun getTokenPrefix(): String = "Bearer "
}