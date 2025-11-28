package org.jeongmo.migration.item.application.service

import org.jeongmo.migration.item.application.dto.*
import org.jeongmo.migration.item.application.error.code.ItemErrorCode
import org.jeongmo.migration.item.application.error.exception.ItemException
import org.jeongmo.migration.item.application.port.inbound.ItemCommandUseCase
import org.jeongmo.migration.item.application.port.inbound.ItemQueryUseCase
import org.jeongmo.migration.item.domain.repository.ItemRepository
import org.slf4j.LoggerFactory
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val transactionTemplate: TransactionTemplate,
): ItemQueryUseCase, ItemCommandUseCase {

    private val logger = LoggerFactory.getLogger(ItemService::class.java)

    @Transactional
    override fun createItem(request: CreateItemRequest): CreateItemResponse {
        val item = itemRepository.save(request.toDomain())
        logger.info("[SUCCESS_CREATE] item-service | id: ${item.id}")
        return CreateItemResponse.fromDomain(item)
    }

    override fun decreaseItemCount(id: Long, retryCount: Int) {
        for (i in 0 until retryCount) {
            try {
                transactionTemplate.execute {
                    val foundItem = itemRepository.findById(id) ?: throw ItemException(ItemErrorCode.NOT_FOUND)
                    foundItem.decreaseItemCount()
                    itemRepository.save(foundItem)
                }
                logger.info("[SUCCESS_DECREASE_ITEM] item-service | id: $id")
                return
            } catch (e: ObjectOptimisticLockingFailureException) {
                logger.warn("[FAIL_DECREASE_ITEM] item-service | retry: ${i + 1} / $retryCount")
                Thread.sleep(100)
            } catch (e: Exception) {
                logger.error("[FAIL_DECREASE_ITEM] item-service | ${e.javaClass}: ${e.message}")
                throw e
            }
        }
        logger.error("[FAIL_DECREASE_ITEM] item-service | id: $id, retry: $retryCount")
        throw ItemException(ItemErrorCode.OPTIMISTIC_LOCKING_ERROR)
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): ItemInfoResponse {
        val item = itemRepository.findById(id) ?: throw ItemException(ItemErrorCode.NOT_FOUND)
        return ItemInfoResponse.fromDomain(item).also { logger.info("[FIND_DOMAIN] item-service | id: ${it.id}") }
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<ItemInfoResponse> {
        return itemRepository.findAll()
            .map {
                ItemInfoResponse.fromDomain(it)
            }
    }

    @Transactional
    override fun updateItem(id: Long, request: UpdateItemRequest): UpdateItemResponse {
        val item = itemRepository.findById(id) ?: throw ItemException(ItemErrorCode.NOT_FOUND)
        request.name?.let { item.changeName(it) }
        request.price?.let { item.changePrice(it) }
        request.discount?.let { item.changeDiscount(it) }
        request.itemStatus?.let { item.changeItemStatus(it) }
        return UpdateItemResponse.fromDomain(itemRepository.save(item)).also { logger.info("[SUCCESS_UPDATE] item-service | id: ${it.id}") }
    }

    @Transactional
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