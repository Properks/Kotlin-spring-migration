package org.jeongmo.practice.domain.item.bought.converter

import org.jeongmo.practice.domain.item.bought.dto.BuyItemResponse
import org.jeongmo.practice.domain.item.bought.dto.FindItemResponse
import org.jeongmo.practice.domain.item.bought.dto.UpdateItemResponse
import org.jeongmo.practice.domain.item.bought.entity.BoughtItem


fun BoughtItem.toBuyItemResponse() = BuyItemResponse(this.id, this.status, this.item.id, this.createdAt)
fun BoughtItem.toFindItemResponse() = FindItemResponse(this.id, this.status, this.item.id, this.createdAt, this.updatedAt)
fun BoughtItem.toUpdateItemResponse() = UpdateItemResponse(this.id, this.status, this.item.id, this.updatedAt)