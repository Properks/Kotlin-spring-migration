package org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.entity.BoughtItemJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface BoughtItemSpringDataJpaRepository: JpaRepository<BoughtItemJpaEntity, Long> {
    fun findByIdAndMemberId(id: Long, memberId: Long): BoughtItemJpaEntity?
    fun findAllByMemberId(memberId: Long): List<BoughtItemJpaEntity>

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BoughtItemJpaEntity e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id AND e.deletedAt IS NULL AND e.memberId = :memberId")
    fun softDeleteBoughtItem(id: Long, memberId: Long): Int
}