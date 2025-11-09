package org.jeongmo.migration.item.application.dto

import org.jeongmo.migration.item.domain.enums.ItemStatus
import org.jeongmo.migration.item.domain.model.Item
import java.time.LocalDateTime

data class CreateItemResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromDomain(item: Item): CreateItemResponse =
            CreateItemResponse(
                id = item.id,
                name = item.name,
                price = item.price,
                createdAt = item.createdAt ?: LocalDateTime.now()
            )
    }
}

data class UpdateItemResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val discount: Double,
    val discountPrice: Long?,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun fromDomain(item: Item): UpdateItemResponse =
            UpdateItemResponse(
                id = item.id,
                name = item.name,
                price = item.price,
                discount = item.discount,
                discountPrice = item.getDiscountPrice(),
                updatedAt = item.updatedAt ?: LocalDateTime.now(),
            )
    }
}

data class ItemInfoResponse(
    val id: Long,
    val name: String,
    val price: Long,
    val discount: Double,
    val discountPrice: Long?,
    val score: Double?,
    val itemStatus: ItemStatus,
) {
    companion object {
        fun fromDomain(item: Item): ItemInfoResponse =
            ItemInfoResponse(
                id = item.id,
                name = item.name,
                price = item.price,
                discount = item.discount,
                discountPrice = item.getDiscountPrice(),
                score = item.score,
                itemStatus = item.itemStatus,
            )
    }
}