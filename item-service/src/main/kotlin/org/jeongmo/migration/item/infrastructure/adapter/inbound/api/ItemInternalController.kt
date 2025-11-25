package org.jeongmo.migration.item.infrastructure.adapter.inbound.api

import org.jeongmo.migration.item.application.dto.ItemInfoResponse
import org.jeongmo.migration.item.application.port.inbound.ItemQueryUseCase
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/internal/api/items")
class ItemInternalController(
    private val itemQueryUseCase: ItemQueryUseCase,
) {

    @GetMapping("/{itemId}")
    fun getItem(@PathVariable itemId: Long): DefaultResponse<ItemInfoResponse> =
        DefaultResponse.ok(itemQueryUseCase.findById(itemId))

}