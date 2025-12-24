package org.jeongmo.migration.item.infrastructure.adapter.out.idempotency

import org.jeongmo.migration.common.utils.idempotency.IdempotencyKeyRepository
import org.jeongmo.migration.common.utils.idempotency.IdempotencyKeyStatus
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Component
class IdempotencyKeyRepositoryImpl: IdempotencyKeyRepository { // TODO: 추후 Redis로 변경 (TTL을 이용하여 해당 Key가 영구적으로 막히는 것을 방지, 요청 내용에 따라 같은 키가 생성되므로 같은 요청(주문)을 다시 하는 경우 작동하도록 TTL로 유지)

    private val map: ConcurrentMap<String, IdempotencyKeyStatus> = ConcurrentHashMap()
    private val log = LoggerFactory.getLogger(IdempotencyKeyRepositoryImpl::class.java)


    override fun setStatus(key: String, status: IdempotencyKeyStatus) {
        map[key] = status
        log.info("Set status idempotency key | key: {}, status: {}", key, status)
    }

    override fun setStatusIfAbsent(key: String, status: IdempotencyKeyStatus): IdempotencyKeyStatus? {
        val result = map.putIfAbsent(key, status)
        result?.let {
            log.info("Set status idempotency key successfully | key: {}, status: {}", key, status)
        } ?: let {
            log.info("Already exist idempotency key status | key: {}", key)
        }
        return result
    }

    override fun deleteKey(key: String) {
        map.remove(key)
        log.info("Delete idempotency key | key: {}", key)
    }
}