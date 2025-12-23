package org.jeongmo.migration.bought.item.application.service

import org.jeongmo.migration.bought.item.application.dto.*
import org.jeongmo.migration.bought.item.application.error.code.BoughtItemErrorCode
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemCommandUseCase
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemQueryUseCase
import org.jeongmo.migration.bought.item.application.port.out.item.ItemServiceClient
import org.jeongmo.migration.bought.item.domain.repository.BoughtItemRepository
import org.jeongmo.migration.common.utils.retry.RetryUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate

@Service
class BoughtItemService(
    private val boughtItemRepository: BoughtItemRepository,
    private val itemServiceClient: ItemServiceClient,
    private val transactionTemplate: TransactionTemplate,
    private val retryUtils: RetryUtils,
): BoughtItemCommandUseCase, BoughtItemQueryUseCase {

    private val log = LoggerFactory.getLogger(BoughtItemService::class.java)

    @Transactional
    override fun buyItem(ownerId: Long, request: BuyItemRequest): BuyItemResponse {
        itemServiceClient.decreaseItemCount(ownerId, request) // TODO: Save 와의 원자성 보장 필요
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

    override fun cancelBoughtItem(ownerId: Long, boughtItemId: Long) {
        val logTitle = "FAIL_TO_DELETE"
        try {
            retryUtils.execute(
                failLogTitle = logTitle
            ) {
                transactionTemplate.execute {
                    val domain = boughtItemRepository.findById(ownerId = ownerId, id = boughtItemId) ?: throw BoughtItemException(BoughtItemErrorCode.NOT_FOUND)
                    domain.markAsDeleted()
                    boughtItemRepository.save(domain)
                }
            }
        } catch (e: BoughtItemException) {
            log.warn("[$logTitle] bought-item-service | id: $boughtItemId")
            throw e
        } catch (e: Exception) {
            log.error("[$logTitle] bought-item-service | ${e.javaClass}: ${e.message}")
            throw BoughtItemException(BoughtItemErrorCode.OPTIMISTIC_LOCK_ERROR)
        }
    }
}