package org.jeongmo.migration.item.application.port.inbound

import org.jeongmo.migration.item.application.dto.*

interface ItemCommandUseCase {
    fun createItem(request: CreateItemRequest): CreateItemResponse
    fun decreaseItemCount(id: Long, request: DecreaseItemStockRequest)
    fun increaseItemCount(id: Long, request: IncreaseItemStockRequest)
    fun updateItem(id: Long, request: UpdateItemRequest): UpdateItemResponse
    fun deleteItem(id: Long)
}