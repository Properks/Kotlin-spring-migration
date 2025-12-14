package org.jeongmo.migration.bought.item.domain.repository

import org.jeongmo.migration.bought.item.domain.model.BoughtItem

interface BoughtItemRepository {
    fun save(boughtItem: BoughtItem): BoughtItem
    fun findById(ownerId: Long, id: Long): BoughtItem?
    fun findAll(ownerId: Long): List<BoughtItem>
    fun delete(ownerId: Long, id: Long): Boolean
}