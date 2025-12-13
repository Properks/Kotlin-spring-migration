package org.jeongmo.migration.bought.item.application.port.out.item

import org.jeongmo.migration.bought.item.application.port.out.item.dto.ItemInfoResponse

interface ItemServiceClient {
    fun getItem(itemId: Long): ItemInfoResponse
    fun decreaseItemCount(item: Long)
}