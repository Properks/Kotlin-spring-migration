package org.jeongmo.practice.domain.item.options.dto

data class CreateItemOptionRequest(val optionName: String, val additionalPrice: Long)
data class UpdateItemOptionRequest(val optionName: String?, val additionalPrice: Long?)