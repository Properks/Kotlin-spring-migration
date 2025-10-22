package org.jeongmo.migration.common.domain

import java.time.LocalDateTime

open class BaseDomain(
    var createAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
) {
}