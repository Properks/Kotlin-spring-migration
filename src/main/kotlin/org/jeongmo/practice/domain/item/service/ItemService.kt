package org.jeongmo.practice.domain.item.service

import org.jeongmo.practice.domain.item.dto.RegisterItemRequest
import org.jeongmo.practice.domain.item.dto.UpdateItemsRequest
import org.jeongmo.practice.domain.item.entity.Item

interface ItemService {
    fun registerItem(request: RegisterItemRequest): Item
    fun findItems(): List<Item>
    fun findItem(id: Long): Item
    fun updateItem(id: Long, request: UpdateItemsRequest): Item
    fun deleteItem(id: Long)
}