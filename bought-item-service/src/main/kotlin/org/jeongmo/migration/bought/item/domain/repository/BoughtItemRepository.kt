package org.jeongmo.migration.bought.item.domain.repository

import org.jeongmo.migration.bought.item.domain.model.BoughtItem

interface BoughtItemRepository {
    fun save(boughtItem: BoughtItem): BoughtItem
    fun findById(id: Long): BoughtItem
    fun findAll(): List<BoughtItem>
    fun delete(id: Long): Boolean
}