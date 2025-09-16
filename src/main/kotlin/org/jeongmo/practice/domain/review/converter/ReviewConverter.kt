package org.jeongmo.practice.domain.review.converter

import org.jeongmo.practice.domain.item.bought.entity.BoughtItem
import org.jeongmo.practice.domain.review.dto.CreateReviewRequest
import org.jeongmo.practice.domain.review.dto.CreateReviewResponse
import org.jeongmo.practice.domain.review.dto.FindReviewResponse
import org.jeongmo.practice.domain.review.entity.Review

fun CreateReviewRequest.toEntity(boughtItem: BoughtItem) = Review(this.score, this.content, boughtItem)
fun Review.toCreateReviewResponse() = CreateReviewResponse(this.id, this.content, this.score, this.boughtItem.id, this.createdAt)
fun Review.toFindReviewResponse() = FindReviewResponse(this.id, this.content, this.score, this.boughtItem.id, this.createdAt, this.updatedAt)