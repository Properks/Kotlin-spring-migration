package org.jeongmo.migration.item.domain.repository

import org.jeongmo.migration.item.domain.model.Item

interface ItemRepository {

    fun save(item: Item): Item
    fun findById(id: Long): Item
    fun findAll(): List<Item>
    fun deleteById(id: Long): Item
}