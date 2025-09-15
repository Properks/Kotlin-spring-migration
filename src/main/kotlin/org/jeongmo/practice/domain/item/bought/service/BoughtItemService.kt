package org.jeongmo.practice.domain.item.bought.service

import org.jeongmo.practice.domain.item.bought.dto.BuyItemRequest
import org.jeongmo.practice.domain.item.bought.dto.UpdateBoughtItemStatusRequest
import org.jeongmo.practice.domain.item.bought.entity.BoughtItem

interface BoughtItemService {

    fun buyItem(username: String, request: BuyItemRequest): BoughtItem
    fun findBoughtItems(username: String): List<BoughtItem>
    fun findBoughtItem(username: String, boughtItemId: Long): BoughtItem
    fun cancelItem(username: String, boughtItemId: Long)
    fun updateStatus(boughtItemId: Long, request: UpdateBoughtItemStatusRequest): BoughtItem
}