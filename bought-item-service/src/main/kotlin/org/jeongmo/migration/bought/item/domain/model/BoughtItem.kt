package org.jeongmo.migration.bought.item.domain.model

import org.jeongmo.migration.common.domain.base.BaseDomain
import java.time.LocalDateTime

class BoughtItem(
    val id: Long = 0L,
    var quantity: Long,
    val memberId: Long,
    val itemId: Long,
    var deletedAt: LocalDateTime? = null,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
): BaseDomain(createdAt, updatedAt) {


}