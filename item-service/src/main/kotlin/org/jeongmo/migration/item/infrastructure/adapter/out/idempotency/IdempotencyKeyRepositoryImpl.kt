package org.jeongmo.migration.item.infrastructure.adapter.out.idempotency

import org.jeongmo.migration.common.utils.idempotency.IdempotencyErrorCode
import org.jeongmo.migration.common.utils.idempotency.IdempotencyException
import org.jeongmo.migration.common.utils.idempotency.IdempotencyKeyRepository
import org.jeongmo.migration.common.utils.idempotency.IdempotencyKeyStatus
import org.jeongmo.migration.common.utils.ttl.TTLRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class IdempotencyKeyRepositoryImpl(
    private val ttlRepository: TTLRepository,
): IdempotencyKeyRepository {

    private val log = LoggerFactory.getLogger(IdempotencyKeyRepositoryImpl::class.java)
    private val ttl: Long = 60 // 1m


    override fun setStatus(key: String, status: IdempotencyKeyStatus) {
        if (ttlRepository.save(key, status, ttl)) {
            log.debug("Set status idempotency key | key: {}, status: {}", key, status)
            return
        }
        log.warn("Fail to save idempotency key status | key: {}", key)
        throw IdempotencyException(IdempotencyErrorCode.FAIL_TO_SET_STATUS)
    }

    override fun setStatusIfAbsent(key: String, status: IdempotencyKeyStatus): IdempotencyKeyStatus? {
        val result = ttlRepository.saveIfAbsent(key, status, ttl)
        result?.let {
            log.debug("Already exist idempotency key status | key: {}, status: {}", key, it)
        } ?: run {
            log.debug("Set status idempotency key successfully | key: {}, status: {}", key, status)
        }
        return result?.let {
            it as? IdempotencyKeyStatus
                ?: throw IdempotencyException(IdempotencyErrorCode.INVALID_VALUE)
        }
    }

    override fun deleteKey(key: String) {
        if (ttlRepository.deleteValue(key)) {
            log.debug("Delete idempotency key | key: {}", key)
        }
        else {
            log.warn("Fail to delete idempotency key | key {}", key)
        }
    }
}