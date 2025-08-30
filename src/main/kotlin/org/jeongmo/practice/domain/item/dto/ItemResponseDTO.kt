package org.jeongmo.practice.domain.item.dto

import org.jeongmo.practice.domain.item.entity.enums.ItemStatus
import java.time.LocalDateTime

data class RegisterItemResponse(val id: Long, val name: String, val price: Long, val discount: Double, val discountPrice: Long?, val createdAt: LocalDateTime)
data class UpdateItemResponse(val id: Long, val name: String, val price: Long, val discount: Double, val discountPrice: Long?, val updatedAt: LocalDateTime)
data class FindItemResponse(val id: Long, val name: String, val price: Long, val discount: Double, val discountPrice: Long?, val score: Double, val itemStatus: ItemStatus, val createdAt: LocalDateTime, val updatedAt: LocalDateTime)