package org.jeongmo.migration.bought.item.application.service

import org.jeongmo.migration.bought.item.application.dto.FindBoughtItemResponse
import org.jeongmo.migration.bought.item.application.error.code.BoughtItemErrorCode
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemQueryUseCase
import org.jeongmo.migration.bought.item.domain.repository.BoughtItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BoughtItemQueryService(
    private val boughtItemRepository: BoughtItemRepository,
): BoughtItemQueryUseCase {

    override fun findById(ownerId: Long, boughtItemId: Long): FindBoughtItemResponse {
        val foundBoughtItem = boughtItemRepository.findById(ownerId, boughtItemId) ?: throw BoughtItemException(
            BoughtItemErrorCode.NOT_FOUND)
        return FindBoughtItemResponse.fromDomain(foundBoughtItem)
    }

    override fun findAll(ownerId: Long): List<FindBoughtItemResponse> {
        val foundBoughtItems = boughtItemRepository.findAll(ownerId)
        return foundBoughtItems.map { FindBoughtItemResponse.fromDomain(it) }
    }
}