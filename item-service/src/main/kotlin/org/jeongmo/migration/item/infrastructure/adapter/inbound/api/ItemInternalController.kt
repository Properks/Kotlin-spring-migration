package org.jeongmo.migration.item.infrastructure.adapter.inbound.api

import org.jeongmo.migration.item.application.dto.ItemInfoResponse
import org.jeongmo.migration.item.application.port.inbound.ItemCommandUseCase
import org.jeongmo.migration.item.application.port.inbound.ItemQueryUseCase
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/internal/api/items")
class ItemInternalController(
    private val itemQueryUseCase: ItemQueryUseCase,
    private val itemCommandUseCase: ItemCommandUseCase,
) {

    @GetMapping("/{itemId}")
    fun getItem(@PathVariable itemId: Long): DefaultResponse<ItemInfoResponse> =
        DefaultResponse.ok(itemQueryUseCase.findById(itemId))

    @PatchMapping("/{itemId}")
    fun decreaseCount(@PathVariable itemId: Long): DefaultResponse<Unit> {
        itemCommandUseCase.decreaseItemCount(itemId);
        return DefaultResponse.noContent()
    }
}