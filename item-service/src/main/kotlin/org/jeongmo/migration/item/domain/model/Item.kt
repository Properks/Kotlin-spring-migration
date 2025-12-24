package org.jeongmo.migration.item.domain.model

import org.jeongmo.migration.common.domain.base.BaseDomain
import org.jeongmo.migration.item.application.error.code.ItemErrorCode
import org.jeongmo.migration.item.application.error.exception.ItemException
import org.jeongmo.migration.item.domain.enums.ItemStatus
import java.time.LocalDateTime

class Item(
    val id: Long = 0L,
    var name: String,
    var price: Long,
    var discount: Double = 0.0,
    var itemCount: Long,
    var itemStatus: ItemStatus,
    var deletedAt: LocalDateTime? = null,
    var score: Double? = null,
    var version: Long = 0L,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
): BaseDomain(createdAt, updatedAt) {

    fun getDiscountPrice(): Long =
        (this.price * (1 - this.discount)).toLong()

    fun changeName(name: String) {
        this.name = name
    }

    fun changePrice(price: Long) {
        this.price = price
    }

    fun changeDiscount(discount: Double) {
        if (discount !in 0.0..1.0) throw ItemException(ItemErrorCode.INVALID_DOMAIN_DATA)
        this.discount = discount
    }

    fun changeItemStatus(itemStatus: ItemStatus) {
        this.itemStatus = itemStatus
    }

    fun changeItemCount(itemCount: Long) {
        this.itemCount = itemCount
    }

    fun decreaseItemCount(quantity: Long) {
        validate(quantity) {it > 0}
        if (this.itemCount < quantity || this.itemStatus == ItemStatus.SOLD) throw ItemException(ItemErrorCode.NO_ITEM_STOCK)
        this.itemCount -= quantity
        if (this.itemCount == 0L) changeItemStatus(ItemStatus.SOLD)
    }

    fun increaseItemCount(quantity: Long) {
        validate(quantity) {it > 0}
        this.itemCount += quantity
        if (this.itemCount > 0 && this.itemStatus == ItemStatus.SOLD) changeItemStatus(ItemStatus.IN_STOCK)
    }

    private fun validate(value: Long, block: (Long)->Boolean) {
        if (!block.invoke(value)) throw ItemException(ItemErrorCode.INVALID_DOMAIN_DATA)
    }
}