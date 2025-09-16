package org.jeongmo.practice.domain.review.service

import org.jeongmo.practice.domain.item.bought.entity.BoughtItem
import org.jeongmo.practice.domain.item.bought.repository.BoughtItemRepository
import org.jeongmo.practice.domain.member.entity.Member
import org.jeongmo.practice.domain.member.repository.MemberRepository
import org.jeongmo.practice.domain.review.converter.toEntity
import org.jeongmo.practice.domain.review.dto.CreateReviewRequest
import org.jeongmo.practice.domain.review.entity.Review
import org.jeongmo.practice.domain.review.repository.ReviewRepository
import org.jeongmo.practice.global.error.code.BoughtItemErrorCode
import org.jeongmo.practice.global.error.code.MemberErrorCode
import org.jeongmo.practice.global.error.code.ReviewErrorCode
import org.jeongmo.practice.global.error.exception.BoughtItemException
import org.jeongmo.practice.global.error.exception.MemberException
import org.jeongmo.practice.global.error.exception.ReviewException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val boughtItemRepository: BoughtItemRepository,
    private val memberRepository: MemberRepository,
): ReviewService {

    override fun createReview(username: String, request: CreateReviewRequest): Review {
        val boughtItem = boughtItemRepository.findById(request.boughtItemId).orElseThrow { BoughtItemException(BoughtItemErrorCode.NOT_FOUND) }
        if (!isOwn(username, boughtItem)) {
            throw BoughtItemException(BoughtItemErrorCode.NOT_YOURS)
        }
        return reviewRepository.save(request.toEntity(boughtItem))
    }

    @Transactional(readOnly = true)
    override fun findItemReviews(itemId: Long): List<Review> {
        return reviewRepository.findAllByItem(itemId)
    }

    @Transactional(readOnly = true)
    override fun findMyReviews(username: String): List<Review> {
        return reviewRepository.findAllByMember(getMember(username).id)
    }

    @Transactional(readOnly = true)
    override fun findReview(reviewId: Long): Review {
        return reviewRepository.findById(reviewId).orElseThrow { ReviewException(ReviewErrorCode.NOT_FOUND) }
    }

    override fun deleteReview(username: String, reviewId: Long) {
        val review: Review = reviewRepository.findById(reviewId).orElseThrow { ReviewException(ReviewErrorCode.NOT_FOUND) }
        if (!isOwn(username, review.boughtItem)) {
            throw ReviewException(ReviewErrorCode.NOT_YOURS)
        }

        reviewRepository.delete(review)
    }

    private fun isOwn(username: String, boughtItem: BoughtItem): Boolean {
        return boughtItem.member.username == username
    }

    private fun getMember(username: String): Member {
        return memberRepository.findByUsername(username) ?: throw MemberException(MemberErrorCode.NOT_FOUND)
    }
}