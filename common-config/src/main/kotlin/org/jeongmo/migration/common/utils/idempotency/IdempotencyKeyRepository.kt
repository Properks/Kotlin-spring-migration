package org.jeongmo.migration.common.utils.idempotency

interface IdempotencyKeyRepository {

    /**
     * Set status of key which is in repository.
     */
    fun setStatus(key: String, status: IdempotencyKeyStatus)

    /**
     * Set status of key which is in repository. But, if key already exist, it will return the status of key. And then if key doesn't exist, it will return null
     * Process a request when returned value is null. if value is not null, request will be denied. And this method will be processed atomically for avoiding race condition.
     * @return null or IdempotencyStatus which is stored in repository
     */
    fun setStatusIfAbsent(key: String, status: IdempotencyKeyStatus): IdempotencyKeyStatus?

    /**
     * Delete key from repository
     */
    fun deleteKey(key: String)

}