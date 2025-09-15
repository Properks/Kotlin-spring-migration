package org.jeongmo.practice.domain.item.bought.controller

import org.jeongmo.practice.domain.item.bought.converter.toBuyItemResponse
import org.jeongmo.practice.domain.item.bought.converter.toFindItemResponse
import org.jeongmo.practice.domain.item.bought.converter.toUpdateItemResponse
import org.jeongmo.practice.domain.item.bought.dto.*
import org.jeongmo.practice.domain.item.bought.service.BoughtItemService
import org.jeongmo.practice.global.annotation.AuthenticatedMember
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/bought-items")
class BoughtItemController(
    private val boughtItemService: BoughtItemService,
) {

    @PostMapping
    fun buyItem(@AuthenticatedMember username: String, @RequestBody request: BuyItemRequest): DefaultResponse<BuyItemResponse> =
        DefaultResponse.ok(boughtItemService.buyItem(username, request).toBuyItemResponse())

    @GetMapping
    fun findItems(@AuthenticatedMember username: String): DefaultResponse<List<FindItemResponse>> =
        DefaultResponse.ok(boughtItemService.findBoughtItems(username).map { it.toFindItemResponse() })

    @GetMapping("/{boughtItemId}")
    fun findItem(@AuthenticatedMember username: String, @PathVariable boughtItemId: Long): DefaultResponse<FindItemResponse> =
        DefaultResponse.ok(boughtItemService.findBoughtItem(username, boughtItemId).toFindItemResponse())

    @PatchMapping("/{boughtItemId}")
    fun updateStatus(@PathVariable boughtItemId: Long, @RequestBody request: UpdateBoughtItemStatusRequest): DefaultResponse<UpdateItemResponse> =
        DefaultResponse.ok(boughtItemService.updateStatus(boughtItemId, request).toUpdateItemResponse())

    @DeleteMapping("/{boughtItemId}")
    fun cancelItem(@AuthenticatedMember username: String, @PathVariable boughtItemId: Long): DefaultResponse<Unit> {
        boughtItemService.cancelItem(username, boughtItemId)
        return DefaultResponse.noContent()
    }
}