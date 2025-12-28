package org.jeongmo.migration.common.utils.compensation.transaction

interface CompensationExecutor {
    fun <T> compensateTransaction(compensationOperator: CompensationOperator<T>)
}
