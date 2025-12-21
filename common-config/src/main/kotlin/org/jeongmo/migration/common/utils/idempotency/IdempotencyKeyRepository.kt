package org.jeongmo.migration.common.utils.idempotency

interface IdempotencyKeyRepository {

    /**
     * Set or save status of key which is in repository
     */
    fun setStatus(key: String, status: IdempotencyKeyStatus)

    /**
     * Get status of key from repository
     * @return null or IdempotencyStatus which found from repository
     */
    fun getStatus(key: String): IdempotencyKeyStatus?

    /**
     * Delete key from repository
     */
    fun deleteKey(key: String)

}