package org.jeongmo.migration.bought.item.domain.model

import org.jeongmo.migration.bought.item.application.error.code.BoughtItemErrorCode
import org.jeongmo.migration.bought.item.application.error.exception.BoughtItemException
import org.jeongmo.migration.common.domain.base.BaseDomain
import org.jeongmo.practice.domain.item.bought.entity.enums.BoughtStatus
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

    fun updateBoughtStatus(boughtStatus: BoughtStatus) {
        if (this.boughtStatus == boughtStatus) {
            throw BoughtItemException(BoughtItemErrorCode.ALREADY_SET)
        }
        this.boughtStatus = boughtStatus
    }
}