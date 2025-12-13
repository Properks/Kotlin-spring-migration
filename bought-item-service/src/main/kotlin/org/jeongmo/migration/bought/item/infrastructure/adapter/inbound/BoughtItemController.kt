package org.jeongmo.migration.bought.item.infrastructure.adapter.inbound

import jakarta.validation.Valid
import org.jeongmo.migration.bought.item.application.dto.*
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemCommandUseCase
import org.jeongmo.migration.bought.item.application.port.inbound.BoughtItemQueryUseCase
import org.jeongmo.migration.common.auth.annotation.LoginUserId
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
@RequestMapping("/bought-items")
class BoughtItemController(
    private val boughtItemCommandUseCase: BoughtItemCommandUseCase,
    private val boughtItemQueryUseCase: BoughtItemQueryUseCase,
) {
    @PostMapping
    fun buyItem(@LoginUserId memberId: Long, @Valid @RequestBody request: BuyItemRequest): DefaultResponse<BuyItemResponse> =
        DefaultResponse.ok(boughtItemCommandUseCase.buyItem(
            memberId = memberId,
            request = request)
        )

    @GetMapping
    fun getBoughtItems(): DefaultResponse<List<FindBoughtItemResponse>> =
        DefaultResponse.ok(boughtItemQueryUseCase.findAll())

    @GetMapping("/{boughtItemId}")
    fun getBoughtItem(@PathVariable("boughtItemId") id: Long): DefaultResponse<FindBoughtItemResponse> =
        DefaultResponse.ok(boughtItemQueryUseCase.findById(id))

    @PatchMapping("/{boughtItemId}")
    fun updateBoughtItem(@PathVariable("boughtItemId") id: Long, @RequestBody request: UpdateItemRequest): DefaultResponse<UpdateItemResponse> =
        DefaultResponse.ok(boughtItemCommandUseCase.updateItemStatus(id, request))

    @DeleteMapping("/{boughtItemId}")
    fun deleteBoughtItem(@PathVariable("boughtItemId") id: Long): DefaultResponse<Unit> {
        boughtItemCommandUseCase.cancelBoughtItem(id)
        return DefaultResponse.noContent()
    }
}