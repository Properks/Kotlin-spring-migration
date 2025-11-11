package org.jeongmo.migration.item.application.dto

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import org.jeongmo.migration.item.domain.enums.ItemStatus
import org.jeongmo.migration.item.domain.model.Item

data class CreateItemRequest(
    @field:NotBlank
    val name: String,
    @field:PositiveOrZero
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
    @field:PositiveOrZero
    val price: Long?,
    @field:DecimalMin("0.0")
    @field:DecimalMax("1.0")
    val discount: Double?,
    val itemStatus: ItemStatus?,
) {
}