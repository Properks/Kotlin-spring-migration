package org.jeongmo.migration.bought.item.application.dto

import org.jeongmo.migration.bought.item.domain.model.BoughtItem
import org.jeongmo.practice.domain.item.bought.entity.enums.BoughtStatus
import java.time.LocalDateTime

data class BuyItemResponse(
    val boughtItemId: Long,
    val quantity: Long,
    val itemId: Long,
    val boughtItemStatus: BoughtStatus,
    val boughtAt: LocalDateTime,
) {
    companion object {
        fun fromDomain(domain: BoughtItem): BuyItemResponse =
            BuyItemResponse(
                boughtItemId = domain.id,
                quantity = domain.quantity,
                itemId = domain.itemId,
                boughtItemStatus = domain.boughtStatus,
                boughtAt = domain.createdAt ?: LocalDateTime.now(),
            )
    }
}

data class UpdateItemResponse(
    val boughtItemId: Long,
    val itemId: Long,
    val boughtItemStatus: BoughtStatus,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun fromDomain(domain: BoughtItem): UpdateItemResponse =
            UpdateItemResponse(
                boughtItemId = domain.id,
                itemId = domain.itemId,
                boughtItemStatus = domain.boughtStatus,
                updatedAt = domain.updatedAt ?: LocalDateTime.now(),
            )
    }
}

data class FindBoughtItemResponse(
    val boughtItemId: Long,
    val itemId: Long,
    val quantity: Long,
    val boughtItemStatus: BoughtStatus,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun fromDomain(domain: BoughtItem): FindBoughtItemResponse =
            FindBoughtItemResponse(
                boughtItemId = domain.id,
                itemId = domain.itemId,
                quantity = domain.quantity,
                boughtItemStatus = domain.boughtStatus,
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt,
            )
    }
}