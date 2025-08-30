package org.jeongmo.practice.domain.item.dto

data class RegisterItemRequest(val name: String, val price: Long, val discount: Double?)
data class UpdateItemsRequest(val name: String, val price: Long, val discount: Double?)