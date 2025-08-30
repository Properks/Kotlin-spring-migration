package org.jeongmo.practice.domain.item.repository

import org.jeongmo.practice.domain.item.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository: JpaRepository<Item, Long> {
}