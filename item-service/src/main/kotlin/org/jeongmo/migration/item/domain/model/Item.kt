package org.jeongmo.migration.item.domain.model

import org.jeongmo.migration.item.domain.enums.ItemStatus
import java.time.LocalDateTime

class Item(
    val id: Long,
    var name: String,
    var price: Long,
    var discount: Double,
    var itemStatus: ItemStatus,
    var deletedAt: LocalDateTime?,
    var score: Double,
) {

    fun getDiscountPrice(): Long =
        (this.price * this.discount).toLong()
}