package org.jeongmo.practice.domain.review.repository

import org.jeongmo.practice.domain.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ReviewRepository: JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.boughtItem.item.id = :itemId")
    fun findAllByItem(@Param("itemId") itemId: Long): List<Review>

    @Query("SELECT r FROM Review r WHERE r.boughtItem.member.id = :memberId")
    fun findAllByMember(@Param("memberId") memberId: Long): List<Review>
}