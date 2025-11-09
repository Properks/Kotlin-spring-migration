package org.jeongmo.migration.item.application.port.inbound

import org.jeongmo.migration.item.application.dto.ItemInfoResponse

interface ItemQueryUseCase {
    fun findById(id: Long): ItemInfoResponse
    fun findAll(): List<ItemInfoResponse>
}