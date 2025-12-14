package org.jeongmo.migration.bought.item.application.port.inbound

import org.jeongmo.migration.bought.item.application.dto.BuyItemRequest
import org.jeongmo.migration.bought.item.application.dto.BuyItemResponse
import org.jeongmo.migration.bought.item.application.dto.UpdateItemRequest
import org.jeongmo.migration.bought.item.application.dto.UpdateItemResponse

interface BoughtItemCommandUseCase {
    fun buyItem(memberId: Long, request: BuyItemRequest): BuyItemResponse
    fun updateItemStatus(ownerId: Long, boughtItemId: Long, request: UpdateItemRequest): UpdateItemResponse
    fun cancelBoughtItem(ownerId: Long, boughtItemId: Long)
}