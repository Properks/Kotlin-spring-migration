package org.jeongmo.migration.item.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.item.infrastructure.adapter.out.jpa.domain.ItemJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ItemSpringDataJpaRepository: JpaRepository<ItemJpaEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE ItemJpaEntity i SET i.deletedAt = CURRENT_TIMESTAMP WHERE i.id = :id AND i.deletedAt IS NULL")
    fun softDeleteById(id: Long): Int
}