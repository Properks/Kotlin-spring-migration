package org.jeongmo.migration.item.application.service

import org.jeongmo.migration.item.application.dto.*
import org.jeongmo.migration.item.application.error.code.ItemErrorCode
import org.jeongmo.migration.item.application.error.exception.ItemException
import org.jeongmo.migration.item.application.port.inbound.ItemCommandUseCase
import org.jeongmo.migration.item.application.port.inbound.ItemQueryUseCase
import org.jeongmo.migration.item.domain.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository,
): ItemQueryUseCase, ItemCommandUseCase {

    private val logger = LoggerFactory.getLogger(ItemService::class.java)

    override fun createItem(request: CreateItemRequest): CreateItemResponse {
        val item = itemRepository.save(request.toDomain())
        logger.info("[SUCCESS_CREATE] item-service | id: ${item.id}")
        return CreateItemResponse.fromDomain(item)
    }

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

    override fun updateItem(id: Long, request: UpdateItemRequest): UpdateItemResponse {
        val item = itemRepository.findById(id) ?: throw ItemException(ItemErrorCode.NOT_FOUND)
        request.name?.let { item.changeName(it) }
        request.price?.let { item.changePrice(it) }
        request.discount?.let { item.changeDiscount(it) }
        request.itemStatus?.let { item.changeItemStatus(it) }
        return UpdateItemResponse.fromDomain(itemRepository.save(item)).also { logger.info("[SUCCESS_UPDATE] item-service | id: ${it.id}") }
    }

    override fun deleteItem(id: Long) {
        try {
            if (!itemRepository.deleteById(id)) {
                throw ItemException(ItemErrorCode.ALREADY_DELETE)
            }
        } catch (e: ItemException) {
            logger.warn("[FAIL_DELETE] item-service | ServerApplicationException: ${e.message}", e)
            throw e
        } catch (e: Exception) {
            logger.error("[FAIL_DELETE] item-service | Unknown Error: ${e.message}", e)
            throw ItemException(ItemErrorCode.FAIL_ITEM_DELETE)
        }
    }
}