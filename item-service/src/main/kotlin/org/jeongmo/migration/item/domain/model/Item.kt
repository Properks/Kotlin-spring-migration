package org.jeongmo.migration.item.domain.model

import org.jeongmo.migration.common.domain.base.BaseDomain
import org.jeongmo.migration.item.domain.enums.ItemStatus
import java.time.LocalDateTime

class Item(
    val id: Long = 0L,
    var name: String,
    var price: Long,
    var discount: Double = 0.0,
    var itemStatus: ItemStatus,
    var deletedAt: LocalDateTime? = null,
    var score: Double? = null,
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
        this.discount = discount
    }

    fun changeItemStatus(itemStatus: ItemStatus) {
        this.itemStatus = itemStatus
    }
}