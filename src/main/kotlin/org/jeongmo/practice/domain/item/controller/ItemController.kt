package org.jeongmo.practice.domain.item.controller

import org.jeongmo.practice.domain.item.converter.toFindItemResponse
import org.jeongmo.practice.domain.item.converter.toRegisterItemResponse
import org.jeongmo.practice.domain.item.converter.toUpdateItemResponse
import org.jeongmo.practice.domain.item.dto.*
import org.jeongmo.practice.domain.item.entity.Item
import org.jeongmo.practice.domain.item.service.ItemService
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemController(
    private val itemService: ItemService,
) {

    @PostMapping
    fun registerItem(@RequestBody request: RegisterItemRequest): DefaultResponse<RegisterItemResponse> = DefaultResponse.ok(itemService.registerItem(request).toRegisterItemResponse())

    @GetMapping
    fun findItems(): DefaultResponse<List<FindItemResponse>> = DefaultResponse.ok(itemService.findItems().map(Item::toFindItemResponse))

    @GetMapping("/{itemId}")
    fun findItem(@PathVariable itemId: Long): DefaultResponse<FindItemResponse> = DefaultResponse.ok(itemService.findItem(itemId).toFindItemResponse())

    @PatchMapping("/{itemId}")
    fun updateItem(@PathVariable itemId: Long, @RequestBody request: UpdateItemsRequest): DefaultResponse<UpdateItemResponse> = DefaultResponse.ok(itemService.updateItem(itemId, request).toUpdateItemResponse())

    @DeleteMapping("/{itemId}")
    fun deleteItem(@PathVariable itemId: Long): DefaultResponse<Unit> {
        itemService.deleteItem(itemId)
        return DefaultResponse.noContent()
    }
}