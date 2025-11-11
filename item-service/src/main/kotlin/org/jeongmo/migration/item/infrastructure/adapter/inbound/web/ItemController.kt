package org.jeongmo.migration.item.infrastructure.adapter.inbound.web

import jakarta.validation.Valid
import org.jeongmo.migration.item.application.dto.*
import org.jeongmo.migration.item.application.port.inbound.ItemCommandUseCase
import org.jeongmo.migration.item.application.port.inbound.ItemQueryUseCase
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
    private val itemQueryUseCase: ItemQueryUseCase,
    private val itemCommandUseCase: ItemCommandUseCase,
) {

    @PostMapping
    fun createItem(@Valid @RequestBody request: CreateItemRequest): DefaultResponse<CreateItemResponse> =
        DefaultResponse.ok(itemCommandUseCase.createItem(request))

    @GetMapping("/{itemId}")
    fun getItem(@PathVariable itemId: Long): DefaultResponse<ItemInfoResponse> =
        DefaultResponse.ok(itemQueryUseCase.findById(itemId))

    @GetMapping
    fun getItems(): DefaultResponse<List<ItemInfoResponse>> =
        DefaultResponse.ok(itemQueryUseCase.findAll())

    @PatchMapping("/{itemId}")
    fun updateItem(@PathVariable itemId: Long, @Valid @RequestBody request: UpdateItemRequest): DefaultResponse<UpdateItemResponse> =
        DefaultResponse.ok(itemCommandUseCase.updateItem(itemId, request))

    @DeleteMapping("/{itemId}")
    fun deleteItem(@PathVariable itemId: Long): DefaultResponse<Unit> {
        itemCommandUseCase.deleteItem(itemId)
        return DefaultResponse.noContent()
    }
}