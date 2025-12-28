package org.jeongmo.migration.bought.item.infrastructure.utils

import org.jeongmo.migration.common.utils.compensation.transaction.CompensationExecutor
import org.jeongmo.migration.common.utils.compensation.transaction.CompensationOperator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class BoughtItemCompensationExecutor: CompensationExecutor {

    private val log = LoggerFactory.getLogger(BoughtItemCompensationExecutor::class.java)

    override fun <T> compensateTransaction(compensationOperator: CompensationOperator<T>) {
        compensationOperator.compensations.forEach {
            try {
                log.warn("[${compensationOperator.title}] bought-item-service | Start compensation transaction. exception-message: ${compensationOperator.exception.message}")
                it.invoke()
                log.warn("[${compensationOperator.title}] bought-item-service | Success compensation transaction. exception-message: ${compensationOperator.exception.message}")
            } catch (e: Exception) {
                log.error("[${compensationOperator.title}] bought-item-compensation-executor | Fail compensation job (cause ${compensationOperator.exception.javaClass.simpleName})", e)
            }
        }
    }
}