package org.jeongmo.migration.item.domain.model

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
) {

    fun getDiscountPrice(): Long =
        (this.price * this.discount).toLong()
}