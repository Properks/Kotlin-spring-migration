package org.jeongmo.practice.domain.review.service

import org.jeongmo.practice.domain.review.dto.CreateReviewRequest
import org.jeongmo.practice.domain.review.entity.Review

interface ReviewService {

    fun createReview(username: String, request: CreateReviewRequest): Review
    fun findItemReviews(itemId: Long): List<Review>
    fun findMyReviews(username: String): List<Review>
    fun findReview(reviewId: Long): Review
    fun deleteReview(username: String, reviewId: Long)

}