package org.jeongmo.migration.item.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.item.infrastructure.adapter.out.jpa.domain.ItemJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemSpringDataJpaRepository: JpaRepository<ItemJpaEntity, Long> {
}