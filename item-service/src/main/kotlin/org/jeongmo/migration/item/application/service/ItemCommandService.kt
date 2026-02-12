package org.jeongmo.migration.item.application.service

import org.jeongmo.migration.common.utils.retry.RetryUtils
import org.jeongmo.migration.item.application.dto.*
import org.jeongmo.migration.item.application.error.code.ItemErrorCode
import org.jeongmo.migration.item.application.error.exception.ItemException
import org.jeongmo.migration.item.application.port.inbound.ItemCommandUseCase
import org.jeongmo.migration.item.domain.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate

@Service
class ItemCommandService(
    private val itemRepository: ItemRepository,
    private val transactionTemplate: TransactionTemplate,
    private val retryUtils: RetryUtils,
): ItemCommandUseCase {

    private val logger = LoggerFactory.getLogger(ItemCommandService::class.java)

    @Transactional
    override fun createItem(ownerId: Long, request: CreateItemRequest): CreateItemResponse {
        val item = itemRepository.save(request.toDomain(ownerId))
        logger.info("[SUCCESS_CREATE] item-service | id: ${item.id}")
        return CreateItemResponse.fromDomain(item)
    }

    @Transactional
    override fun updateItem(ownerId: Long, itemId: Long, request: UpdateItemRequest): UpdateItemResponse {
        val item = itemRepository.findById(itemId) ?: throw ItemException(ItemErrorCode.NOT_FOUND)

        if (!item.checkOwner(ownerId)) {
            logger.warn("[NOT_OWNER] item-service | id: ${item.id}")
            throw ItemException(ItemErrorCode.NOT_OWNER)
        }

        request.name?.let { item.changeName(it) }
        request.price?.let { item.changePrice(it) }
        request.discount?.let { item.changeDiscount(it) }
        request.itemStatus?.let { item.changeItemStatus(it) }
        return UpdateItemResponse.fromDomain(itemRepository.save(item)).also { logger.info("[SUCCESS_UPDATE] item-service | id: ${it.id}") }
    }

    @Transactional
    override fun deleteItem(ownerId: Long, itemId: Long) {
        try {
            itemRepository.findById(itemId)?.also {
                if (!it.checkOwner(ownerId)) {
                    throw ItemException(ItemErrorCode.NOT_OWNER)
                }
            }
            if (!itemRepository.deleteById(itemId)) {
                throw ItemException(ItemErrorCode.ALREADY_DELETE)
            }
        } catch (e: ItemException) {
            logger.warn("[FAIL_DELETE] item-service | ServerApplicationException: ${e.message}", e)
            throw e
        } catch (e: Exception) {
            logger.error("[FAIL_DELETE] item-service | Unknown Error: ${e.message}", e)
            throw ItemException(ItemErrorCode.FAIL_ITEM_DELETE, e)
        }
    }


    override fun decreaseItemCount(itemId: Long, request: DecreaseItemStockRequest) {
        val logTitle = "FAIL_DECREASE_ITEM_COUNT"
        try {
            retryUtils.execute(
                failLogTitle = logTitle
            ) {
                transactionTemplate.execute {
                    val foundItem = itemRepository.findById(itemId) ?: throw ItemException(ItemErrorCode.NOT_FOUND)
                    foundItem.decreaseItemCount(quantity = request.quantity)
                    itemRepository.save(foundItem)
                }
            }
        } catch (e: ItemException) {
            throw e
        } catch (e: Exception) {
            logger.warn("[$logTitle] item-service | id: $itemId, ${e.javaClass}: ${e.message}")
            throw ItemException(ItemErrorCode.OPTIMISTIC_LOCKING_ERROR, e)
        }
    }

    override fun increaseItemCount(itemId: Long, request: IncreaseItemStockRequest) {
        val logTitle = "FAIL_INCREASE_STOCK"
        try {
            retryUtils.execute(
                failLogTitle = logTitle
            ) {
                transactionTemplate.execute {
                    val foundItem = itemRepository.findById(itemId) ?: throw ItemException(ItemErrorCode.NOT_FOUND)
                    foundItem.increaseItemCount(quantity = request.quantity)
                    itemRepository.save(foundItem)
                }
            }
        } catch (e: ItemException) {
            throw e
        } catch (e: Exception) {
            logger.warn("[$logTitle] item-service | id: $itemId")
            throw ItemException(ItemErrorCode.OPTIMISTIC_LOCKING_ERROR, e)
        }
    }
}