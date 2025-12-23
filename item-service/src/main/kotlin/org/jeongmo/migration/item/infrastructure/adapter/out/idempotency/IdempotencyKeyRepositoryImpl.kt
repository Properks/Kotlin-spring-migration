package org.jeongmo.migration.item.infrastructure.adapter.out.idempotency

import org.jeongmo.migration.common.utils.idempotency.IdempotencyKeyRepository
import org.jeongmo.migration.common.utils.idempotency.IdempotencyKeyStatus
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class IdempotencyKeyRepositoryImpl: IdempotencyKeyRepository { // TODO: 추후 Redis로 변경 (TTL을 이용하여 해당 Key가 영구적으로 막히는 것을 방지, 요청 내용에 따라 같은 키가 생성되므로 같은 요청(주문)을 다시 하는 경우 작동하도록 TTL로 유지)

    private val map: MutableMap<String, IdempotencyKeyStatus> = mutableMapOf()
    private val log = LoggerFactory.getLogger(IdempotencyKeyRepositoryImpl::class.java)

    override fun setStatus(key: String, status: IdempotencyKeyStatus) {
        map[key] = status
        log.info("Save idempotency | key: {}, status: {}", key, status)
    }

    override fun getStatus(key: String): IdempotencyKeyStatus? {
        val result = map[key]
        log.info("Get idempotency key | key: {}, status: {}", key, result)
        return result
    }

    override fun deleteKey(key: String) {
        map.remove(key)
        log.info("Delete idempotency key | key: {}", key)
    }
}