package org.jeongmo.migration.bought.item.application.service

import org.jeongmo.migration.bought.item.application.dto.*
import org.jeongmo.migration.bought.item.application.error.code.BoughtItemErrorCode
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemCommandUseCase
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemQueryUseCase
import org.jeongmo.migration.bought.item.application.port.out.item.ItemServiceClient
import org.jeongmo.migration.bought.item.domain.repository.BoughtItemRepository
import org.jeongmo.migration.common.utils.compensation.transaction.CompensationExecutor
import org.jeongmo.migration.common.utils.compensation.transaction.CompensationOperator
import org.jeongmo.migration.common.utils.retry.RetryUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate

@Service
class BoughtItemService(
    private val boughtItemRepository: BoughtItemRepository,
    private val itemServiceClient: ItemServiceClient,
    private val compensationExecutor: CompensationExecutor,
    private val transactionTemplate: TransactionTemplate,
    private val retryUtils: RetryUtils,
): BoughtItemCommandUseCase, BoughtItemQueryUseCase {

    private val log = LoggerFactory.getLogger(BoughtItemService::class.java)

    @Transactional
    override fun buyItem(ownerId: Long, request: BuyItemRequest): BuyItemResponse {
        var decreaseStock = false
        try {
            itemServiceClient.decreaseItemCount(ownerId, request.itemId, request.quantity)
            decreaseStock = true
            val boughtItem = boughtItemRepository.save(request.toDomain(ownerId))
            return BuyItemResponse.fromDomain(boughtItem)
        } catch (e: Exception) {
            compensationExecutor.compensateTransaction(
                CompensationOperator(
                    title = "BUY_ITEM",
                    exception = e,
                    compensations = listOf {
                        if (decreaseStock) {
                            itemServiceClient.increaseItemCount(
                                ownerId = ownerId,
                                itemId = request.itemId,
                                quantity = request.quantity
                            )
                        }
                    }
                )
            )
            throw BoughtItemException(BoughtItemErrorCode.FAIL_TO_BUY_ITEM)
        }
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

    override fun cancelBoughtItem(ownerId: Long, boughtItemId: Long) {
        var stockIncrease = false
        val foundBoughtItem = boughtItemRepository.findById(ownerId = ownerId, id = boughtItemId) ?: throw BoughtItemException(BoughtItemErrorCode.NOT_FOUND)
        try {
            itemServiceClient.increaseItemCount(ownerId = ownerId, itemId = foundBoughtItem.itemId, quantity = foundBoughtItem.quantity)
            stockIncrease = true
            retryUtils.execute(
                failLogTitle = "FAIL_TO_DELETE"
            ) {
                transactionTemplate.execute {
                    val domain = boughtItemRepository.findById(ownerId = ownerId, id = boughtItemId) ?: throw BoughtItemException(BoughtItemErrorCode.NOT_FOUND)
                    domain.markAsDeleted()
                    boughtItemRepository.save(domain)
                }
            }
        } catch (e: Exception) {
            compensationExecutor.compensateTransaction(
                CompensationOperator(
                    title = "CANCEL_ITEM",
                    exception = e,
                    compensations = listOf {
                        if (stockIncrease) {
                            itemServiceClient.decreaseItemCount(
                                ownerId = ownerId,
                                itemId = foundBoughtItem.itemId,
                                quantity = foundBoughtItem.quantity
                            )
                        }
                    }
                )
            )
            throw BoughtItemException(BoughtItemErrorCode.FAIL_TO_CANCEL_ITEM)
        }
    }
}