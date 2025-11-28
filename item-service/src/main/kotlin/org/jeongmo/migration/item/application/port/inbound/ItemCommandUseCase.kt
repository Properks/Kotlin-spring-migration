package org.jeongmo.migration.item.application.port.inbound

import org.jeongmo.migration.item.application.dto.CreateItemRequest
import org.jeongmo.migration.item.application.dto.CreateItemResponse
import org.jeongmo.migration.item.application.dto.UpdateItemRequest
import org.jeongmo.migration.item.application.dto.UpdateItemResponse

interface ItemCommandUseCase {
    fun createItem(request: CreateItemRequest): CreateItemResponse
    fun decreaseItemCount(id: Long, retryCount: Int = 10)
    fun updateItem(id: Long, request: UpdateItemRequest): UpdateItemResponse
    fun deleteItem(id: Long)
}