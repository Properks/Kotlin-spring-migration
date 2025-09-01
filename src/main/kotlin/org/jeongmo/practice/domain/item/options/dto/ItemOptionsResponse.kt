package org.jeongmo.practice.domain.item.options.dto

import java.time.LocalDateTime

data class CreateItemOptionResponse(val itemId: Long, val itemOptionId: Long, val optionName: String, val additionalPrice: Long, val createdAt: LocalDateTime)
data class FindItemOptionResponse(val itemOptionId: Long, val optionName: String, val additionalPrice: Long)
data class UpdateItemOptionResponse(val itemId: Long, val itemOptionId: Long, val optionName: String, val additionalPrice: Long, val updatedAt: LocalDateTime)
