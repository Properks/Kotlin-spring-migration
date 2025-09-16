package org.jeongmo.practice.domain.review.dto

data class CreateReviewRequest(val score: Double, val content: String, val boughtItemId: Long)