package org.jeongmo.migration.bought.item.domain.model

import org.jeongmo.migration.bought.item.application.error.code.BoughtItemErrorCode
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException
import org.jeongmo.migration.bought.item.domain.enums.BoughtStatus
import org.jeongmo.migration.common.domain.base.BaseDomain
import java.time.LocalDateTime

class BoughtItem(
    val id: Long = 0L,
    var quantity: Long,
    val memberId: Long,
    val itemId: Long,
    var boughtStatus: BoughtStatus,
    var deletedAt: LocalDateTime? = null,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
): BaseDomain(createdAt, updatedAt) {

    init {
        if (quantity <= 0 || memberId <= 0 || itemId <= 0) {
            throw BoughtItemException(BoughtItemErrorCode.INVALID_DATA)
        }
    }

    fun updateBoughtStatus(boughtStatus: BoughtStatus) {
        if (this.boughtStatus == boughtStatus) {
            throw BoughtItemException(BoughtItemErrorCode.ALREADY_SET)
        }
        this.boughtStatus = boughtStatus
    }
}