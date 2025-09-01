package org.jeongmo.practice.domain.item.options.converter

import org.jeongmo.practice.domain.item.entity.Item
import org.jeongmo.practice.domain.item.options.dto.CreateItemOptionRequest
import org.jeongmo.practice.domain.item.options.dto.CreateItemOptionResponse
import org.jeongmo.practice.domain.item.options.dto.FindItemOptionResponse
import org.jeongmo.practice.domain.item.options.dto.UpdateItemOptionResponse
import org.jeongmo.practice.domain.item.options.entity.ItemOption

fun CreateItemOptionRequest.toEntity(item: Item) = ItemOption(this.optionName, this.additionalPrice, item)

fun ItemOption.toCreateItemOptionResponse() = CreateItemOptionResponse(this.item.id, this.id, this.optionName, this.additionalPrice, this.createdAt)
fun ItemOption.toFindItemOptionResponse() = FindItemOptionResponse(this.id, this.optionName, this.additionalPrice)
fun ItemOption.toUpdateItemOptionResponse() = UpdateItemOptionResponse(this.item.id, this.id, this.optionName, this.additionalPrice, this.updatedAt)