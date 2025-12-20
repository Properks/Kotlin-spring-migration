package org.jeongmo.migration.common.utils.retry

import org.slf4j.LoggerFactory
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Component

@Component
class RetryUtils {

    private val log = LoggerFactory.getLogger(RetryUtils::class.java)

    fun <T> execute(
        failLogTitle: String = "OPERATION",
        maxAttempts: Int = 10,
        backoffMillis: Long = 100,
        block: () -> T
    ): T {
        for (i in 1..maxAttempts) {
            try {
                val result = block()
                log.info("[$failLogTitle] Success")
                return result
            } catch (e: ObjectOptimisticLockingFailureException) {
                if (i == maxAttempts) {
                    log.error("[$failLogTitle] Max retries exceeded")
                    throw e
                }
                log.debug("[$failLogTitle] Retry $i/$maxAttempts")
                Thread.sleep(backoffMillis)
            }
            catch (e: Exception) {
                log.error("[$failLogTitle] item-service | ${e.javaClass}: ${e.message}")
                throw e
            }
        }
        error("Unreachable")
    }
}