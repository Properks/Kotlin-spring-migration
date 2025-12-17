package org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.entity.BoughtItemJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoughtItemSpringDataJpaRepository: JpaRepository<BoughtItemJpaEntity, Long> {
    fun findByIdAndMemberId(id: Long, memberId: Long): BoughtItemJpaEntity?
    fun findAllByMemberId(memberId: Long): List<BoughtItemJpaEntity>
}