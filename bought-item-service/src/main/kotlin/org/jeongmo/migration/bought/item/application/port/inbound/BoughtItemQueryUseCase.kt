package org.jeongmo.migration.bought.item.application.port.inbound

import org.jeongmo.migration.bought.item.application.dto.FindBoughtItemResponse

interface BoughtItemQueryUseCase {

    fun findById(ownerId: Long, boughtItemId: Long): FindBoughtItemResponse
    fun findAll(ownerId: Long): List<FindBoughtItemResponse>
}