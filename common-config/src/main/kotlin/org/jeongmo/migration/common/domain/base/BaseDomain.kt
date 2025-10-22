package org.jeongmo.migration.common.domain.base

import java.time.LocalDateTime

open class BaseDomain(
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
) {
}