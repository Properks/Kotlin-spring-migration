package org.jeongmo.practice.domain.item.converter

import org.jeongmo.practice.domain.item.dto.FindItemResponse
import org.jeongmo.practice.domain.item.dto.RegisterItemRequest
import org.jeongmo.practice.domain.item.dto.RegisterItemResponse
import org.jeongmo.practice.domain.item.dto.UpdateItemResponse
import org.jeongmo.practice.domain.item.entity.Item


fun RegisterItemRequest.toEntity(): Item = Item(this.name, this.price)
fun Item.toRegisterItemResponse(): RegisterItemResponse = RegisterItemResponse(this.id, this.name, this.price, this.discount, this.discountPrice, this.createdAt)
fun Item.toFindItemResponse(): FindItemResponse = FindItemResponse(this.id, this.name, this.price, this.discount, this.discountPrice, this.score, this.itemStatus, this.createdAt, this.updatedAt)
fun Item.toUpdateItemResponse(): UpdateItemResponse = UpdateItemResponse(this.id, this.name, this.price, this.discount, this.discountPrice, this.updatedAt)