package org.jeongmo.migration.item.application.service

import org.jeongmo.migration.item.application.dto.ItemInfoResponse
import org.jeongmo.migration.item.application.error.code.ItemErrorCode
import org.jeongmo.migration.item.application.error.exception.ItemException
import org.jeongmo.migration.item.application.port.inbound.ItemQueryUseCase
import org.jeongmo.migration.item.domain.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemQueryService(
    private val itemRepository: ItemRepository,
):ItemQueryUseCase {

    private val logger = LoggerFactory.getLogger(ItemQueryService::class.java)

    override fun findById(id: Long): ItemInfoResponse {
        val item = itemRepository.findById(id) ?: throw ItemException(ItemErrorCode.NOT_FOUND)
        return ItemInfoResponse.fromDomain(item).also { logger.info("[FIND_DOMAIN] item-service | id: ${it.id}") }
    }

    override fun findAll(): List<ItemInfoResponse> {
        return itemRepository.findAll()
            .map {
                ItemInfoResponse.fromDomain(it)
            }
    }
}