package org.jeongmo.migration.common.utils.compensation.transaction

import java.lang.Exception

data class CompensationOperator<T>(
    val title: String,
    val exception: Exception,
    val compensations: List<() -> T>,
)
