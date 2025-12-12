package org.jeongmo.migration.bought.item.application.dto

import org.jeongmo.migration.bought.item.domain.model.BoughtItem
import org.jeongmo.practice.domain.item.bought.entity.enums.BoughtStatus

data class BuyItemRequest(
    val itemId: Long,
    val quantity: Long,
) {
    fun toDomain(memberId: Long) =
        BoughtItem(
            quantity = this.quantity,
            itemId = itemId,
            memberId = memberId,
            boughtStatus = BoughtStatus.ACCEPTING
        )
}

data class UpdateItemRequest(
    val boughtItemStatus: BoughtStatus
)