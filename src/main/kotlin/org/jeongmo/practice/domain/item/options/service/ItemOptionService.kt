package org.jeongmo.practice.domain.item.options.service

import org.jeongmo.practice.domain.item.options.dto.CreateItemOptionRequest
import org.jeongmo.practice.domain.item.options.dto.UpdateItemOptionRequest
import org.jeongmo.practice.domain.item.options.entity.ItemOption

interface ItemOptionService {
    fun createItemOption(itemId: Long, request: CreateItemOptionRequest): ItemOption
    fun findItemOptions(itemId: Long): List<ItemOption>
    fun findItemOption(itemOptionId: Long): ItemOption
    fun updateItemOption(itemOptionId: Long, request: UpdateItemOptionRequest): ItemOption
    fun deleteItemOption(itemOptionId: Long)
}