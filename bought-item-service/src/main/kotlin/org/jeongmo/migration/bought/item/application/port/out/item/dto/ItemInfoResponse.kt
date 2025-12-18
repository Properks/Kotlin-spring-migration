package org.jeongmo.migration.bought.item.application.port.out.item.dto

import org.jeongmo.migration.bought.item.application.port.out.item.enums.ItemStatus

data class ItemInfoResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val discount: Double,
    val discountPrice: Long?,
    val score: Double?,
    val itemStatus: ItemStatus,
) {
}