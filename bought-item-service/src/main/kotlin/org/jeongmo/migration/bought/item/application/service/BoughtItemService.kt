package org.jeongmo.migration.bought.item.application.service

import org.jeongmo.migration.bought.item.application.dto.*
import org.jeongmo.migration.bought.item.application.error.code.BoughtItemErrorCode
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemCommandUseCase
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemQueryUseCase
import org.jeongmo.migration.bought.item.application.port.out.item.ItemServiceClient
import org.jeongmo.migration.bought.item.domain.repository.BoughtItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoughtItemService(
    private val boughtItemRepository: BoughtItemRepository,
    private val itemServiceClient: ItemServiceClient
): BoughtItemCommandUseCase, BoughtItemQueryUseCase {

    private val log = LoggerFactory.getLogger(BoughtItemService::class.java)

    @Transactional
    override fun buyItem(ownerId: Long, request: BuyItemRequest): BuyItemResponse {
        itemServiceClient.decreaseItemCount(request.itemId) // TODO: Save 와의 원자성 보장 필요
        val boughtItem = boughtItemRepository.save(request.toDomain(ownerId))
        return BuyItemResponse.fromDomain(boughtItem)
    }

    @Transactional(readOnly = true)
    override fun findById(ownerId: Long, boughtItemId: Long): FindBoughtItemResponse {
        val foundBoughtItem = boughtItemRepository.findById(ownerId, boughtItemId) ?: throw BoughtItemException(BoughtItemErrorCode.NOT_FOUND)
        return FindBoughtItemResponse.fromDomain(foundBoughtItem)
    }

    @Transactional(readOnly = true)
    override fun findAll(ownerId: Long): List<FindBoughtItemResponse> {
        val foundBoughtItems = boughtItemRepository.findAll(ownerId)
        return foundBoughtItems.map { FindBoughtItemResponse.fromDomain(it) }
    }

    @Transactional
    override fun updateItemStatus(ownerId: Long, boughtItemId: Long, request: UpdateItemRequest): UpdateItemResponse {
        val foundItem = boughtItemRepository.findById(ownerId, boughtItemId) ?: throw BoughtItemException(BoughtItemErrorCode.NOT_FOUND)
        foundItem.updateBoughtStatus(boughtStatus = request.boughtItemStatus)
        val savedBoughtItem= boughtItemRepository.save(foundItem)
        return UpdateItemResponse.fromDomain(savedBoughtItem)
    }

    @Transactional
    override fun cancelBoughtItem(ownerId: Long, boughtItemId: Long) {
        for (i in 1..10) {
            try {
                val domain = boughtItemRepository.findById(ownerId = ownerId, id = boughtItemId) ?: throw BoughtItemException(BoughtItemErrorCode.NOT_FOUND)
                domain.markAsDeleted()
                boughtItemRepository.save(domain)
                return
            } catch (e: BoughtItemException) {
                log.warn("[FAIL_TO_DELETE] bought-item-service | Cannot find or delete entity")
                throw e
            } catch (e: Exception) {
                log.warn("[RETRY_DELETE] bought-item-service | retry to delete entity {} / 10", i)
            }
        }
        throw BoughtItemException(BoughtItemErrorCode.OPTIMISTIC_LOCK_ERROR)
    }
}