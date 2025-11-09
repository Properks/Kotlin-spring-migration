package org.jeongmo.migration.item.application.dto

import org.jeongmo.migration.item.domain.enums.ItemStatus
import org.jeongmo.migration.item.domain.model.Item

data class CreateItemRequest(
    val name: String,
    val price: Long,
) {
    fun toDomain(): Item {
        return Item(
            name = this.name,
            price = this.price,
            itemStatus = ItemStatus.IN_STOCK,
        )
    }
}

data class UpdateItemRequest(
    val name: String?,
    val price: Long?,
    val discount: Double?,
    val itemStatus: ItemStatus?,
) {
}