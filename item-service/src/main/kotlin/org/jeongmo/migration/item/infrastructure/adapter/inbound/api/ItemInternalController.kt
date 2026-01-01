package org.jeongmo.migration.item.infrastructure.adapter.inbound.api

import jakarta.validation.Valid
import org.jeongmo.migration.item.application.dto.DecreaseItemStockRequest
import org.jeongmo.migration.item.application.dto.IncreaseItemStockRequest
import org.jeongmo.migration.item.application.dto.ItemInfoResponse
import org.jeongmo.migration.item.application.port.inbound.ItemCommandUseCase
import org.jeongmo.migration.item.application.port.inbound.ItemQueryUseCase
import org.namul.api.payload.response.supports.DefaultResponse
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

    /**
     * 상품 개수 1 감소
     * 낙관적 락으로 실패 시 재시도, 재시도 횟수 내에 실패 시 500에러
     */
    @PatchMapping("/{itemId}/decrease-stock")
    fun decreaseCount(@PathVariable("itemId") itemId: Long, @Valid @RequestBody request: DecreaseItemStockRequest): DefaultResponse<Unit> {
        itemCommandUseCase.decreaseItemCount(itemId, request)
        return DefaultResponse.noContent()
    }

    /**
     * 상품 개수 1 증가
     * 낙관적 락으로 실패 시 재시도, 재시도 횟수 내에 실패 시 500에러
     */
    @PatchMapping("/{itemId}/increase-stock")
    fun increaseCount(@PathVariable("itemId") id: Long, @Valid @RequestBody request: IncreaseItemStockRequest): DefaultResponse<Unit> {
        itemCommandUseCase.increaseItemCount(id, request)
        return DefaultResponse.noContent()
    }
}