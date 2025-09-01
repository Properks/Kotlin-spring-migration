package org.jeongmo.practice.domain.item.options.controller

import org.jeongmo.practice.domain.item.options.converter.toCreateItemOptionResponse
import org.jeongmo.practice.domain.item.options.converter.toFindItemOptionResponse
import org.jeongmo.practice.domain.item.options.converter.toUpdateItemOptionResponse
import org.jeongmo.practice.domain.item.options.dto.*
import org.jeongmo.practice.domain.item.options.entity.ItemOption
import org.jeongmo.practice.domain.item.options.service.ItemOptionService
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/items")
class ItemOptionController(
    private val itemOptionService: ItemOptionService,
) {

    @PostMapping("/{itemId}/options")
    fun createItemOption(@PathVariable itemId: Long, @RequestBody request: CreateItemOptionRequest): DefaultResponse<CreateItemOptionResponse> =
        DefaultResponse.ok(itemOptionService.createItemOption(itemId, request).toCreateItemOptionResponse())

    @GetMapping("/{itemId}/options")
    fun findOptions(@PathVariable itemId: Long): DefaultResponse<List<FindItemOptionResponse>> =
        DefaultResponse.ok(itemOptionService.findItemOptions(itemId).map(ItemOption::toFindItemOptionResponse))

    @GetMapping("/options/{itemOptionId}")
    fun findOption(@PathVariable itemOptionId: Long): DefaultResponse<FindItemOptionResponse> =
        DefaultResponse.ok(itemOptionService.findItemOption(itemOptionId).toFindItemOptionResponse())

    @PatchMapping("/options/{itemOptionId}")
    fun updateOption(@PathVariable itemOptionId: Long, @RequestBody request: UpdateItemOptionRequest): DefaultResponse<UpdateItemOptionResponse> =
        DefaultResponse.ok(itemOptionService.updateItemOption(itemOptionId, request).toUpdateItemOptionResponse())

    @DeleteMapping("/options/{itemOptionId}")
    fun deleteOption(@PathVariable itemOptionId: Long): DefaultResponse<Unit> {
        itemOptionService.deleteItemOption(itemOptionId)
        return DefaultResponse.noContent()
    }

}