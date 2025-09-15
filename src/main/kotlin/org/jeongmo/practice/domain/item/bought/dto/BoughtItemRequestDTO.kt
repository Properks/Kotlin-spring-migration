package org.jeongmo.practice.domain.item.bought.dto

import org.jeongmo.practice.domain.item.bought.entity.enums.BoughtStatus

data class BuyItemRequest(val boughtItemId: Long, val itemOptionId: Long?, val quantity: Long)
data class UpdateBoughtItemStatusRequest(val status: BoughtStatus)