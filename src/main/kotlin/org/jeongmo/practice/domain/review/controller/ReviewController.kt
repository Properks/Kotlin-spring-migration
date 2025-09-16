package org.jeongmo.practice.domain.review.controller

import org.jeongmo.practice.domain.review.converter.toCreateReviewResponse
import org.jeongmo.practice.domain.review.converter.toFindReviewResponse
import org.jeongmo.practice.domain.review.dto.CreateReviewRequest
import org.jeongmo.practice.domain.review.dto.CreateReviewResponse
import org.jeongmo.practice.domain.review.dto.FindReviewResponse
import org.jeongmo.practice.domain.review.service.ReviewService
import org.jeongmo.practice.global.annotation.AuthenticatedMember
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reviews")
class ReviewController(
    private val reviewService: ReviewService
) {

    @PostMapping
    fun createReview(@AuthenticatedMember username: String, @RequestBody request: CreateReviewRequest): DefaultResponse<CreateReviewResponse> =
        DefaultResponse.ok(reviewService.createReview(username, request).toCreateReviewResponse())

    @GetMapping("/items/{itemId}")
    fun findItemReview(@PathVariable itemId: Long): DefaultResponse<List<FindReviewResponse>> =
        DefaultResponse.ok(reviewService.findItemReviews(itemId).map { it.toFindReviewResponse() })

    @GetMapping("/me")
    fun findMyReview(@AuthenticatedMember username: String): DefaultResponse<List<FindReviewResponse>> =
        DefaultResponse.ok(reviewService.findMyReviews(username).map {it.toFindReviewResponse()})

    @GetMapping("/{reviewId}")
    fun findReview(@PathVariable reviewId: Long): DefaultResponse<FindReviewResponse> =
        DefaultResponse.ok(reviewService.findReview(reviewId).toFindReviewResponse())

    @DeleteMapping("/{reviewId}")
    fun deleteReview(@AuthenticatedMember username: String, @PathVariable reviewId: Long): DefaultResponse<Unit> {
        reviewService.deleteReview(username, reviewId)
        return DefaultResponse.noContent()
    }
}