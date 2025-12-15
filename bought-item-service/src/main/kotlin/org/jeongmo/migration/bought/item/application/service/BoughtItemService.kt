package org.jeongmo.migration.bought.item.application.service

import org.jeongmo.migration.bought.item.application.dto.*
import org.jeongmo.migration.bought.item.application.error.code.BoughtItemErrorCode
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemCommandUseCase
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemQueryUseCase
import org.jeongmo.migration.bought.item.application.port.out.item.ItemServiceClient
import org.jeongmo.migration.bought.item.domain.repository.BoughtItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoughtItemService(
    private val boughtItemRepository: BoughtItemRepository,
    private val itemServiceClient: ItemServiceClient
): BoughtItemCommandUseCase, BoughtItemQueryUseCase {

    @Transactional
    override fun buyItem(ownerId: Long, request: BuyItemRequest): BuyItemResponse {
        val boughtItem = boughtItemRepository.save(request.toDomain(ownerId))
        itemServiceClient.decreaseItemCount(request.itemId)
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
        if (!boughtItemRepository.delete(ownerId, boughtItemId)) {
            throw BoughtItemException(BoughtItemErrorCode.ALREADY_DELETE)
        }
    }
}