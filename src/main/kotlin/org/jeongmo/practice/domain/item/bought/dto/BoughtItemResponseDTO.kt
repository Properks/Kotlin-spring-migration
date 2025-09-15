package org.jeongmo.practice.domain.item.bought.dto

import org.jeongmo.practice.domain.item.bought.entity.enums.BoughtStatus
import java.time.LocalDateTime

data class BuyItemResponse(val id: Long, val status: BoughtStatus, val itemId: Long, val createdAt: LocalDateTime)
data class FindItemResponse(val id: Long, val status: BoughtStatus, val itemId: Long, val createdAt: LocalDateTime, val updatedAt: LocalDateTime)
data class UpdateItemResponse(val id: Long, val status: BoughtStatus, val itemId: Long, val updatedAt:LocalDateTime)
