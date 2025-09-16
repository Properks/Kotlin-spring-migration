package org.jeongmo.practice.domain.review.dto

import java.time.LocalDateTime

data class CreateReviewResponse(val reviewId: Long, val content: String, val score: Double, val boughtItemId: Long, val createdAt: LocalDateTime)
data class FindReviewResponse(val reviewId: Long, val content: String, val score: Double, val boughtItemId: Long, val createdAt: LocalDateTime, val updatedAt: LocalDateTime)