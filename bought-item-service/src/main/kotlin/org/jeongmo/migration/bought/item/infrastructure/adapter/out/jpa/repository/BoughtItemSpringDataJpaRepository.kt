package org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.entity.BoughtItemJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface BoughtItemSpringDataJpaRepository: JpaRepository<BoughtItemJpaEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BoughtItemJpaEntity e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id AND e.deletedAt IS NULL")
    fun softDeleteBoughtItem(id: Long): Int
}