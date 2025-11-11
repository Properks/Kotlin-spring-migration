package org.jeongmo.migration.item.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.item.domain.model.Item
import org.jeongmo.migration.item.domain.repository.ItemRepository
import org.jeongmo.migration.item.infrastructure.adapter.out.jpa.mapper.ItemJpaMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ItemJpaRepository(
    private val itemSpringDataJpaRepository: ItemSpringDataJpaRepository,
    private val itemJpaMapper: ItemJpaMapper,
): ItemRepository {

    override fun save(item: Item): Item {
        val itemEntity = itemJpaMapper.fromDomain(item)
        return itemJpaMapper.toDomain(
            itemJpaEntity = itemSpringDataJpaRepository.save(itemEntity)
        )
    }

    override fun findById(id: Long): Item? {
        val foundItem = itemSpringDataJpaRepository.findById(id).orElse(null)
        return foundItem?.let { itemJpaMapper.toDomain(it) }
    }

    override fun findAll(): List<Item> {
        val foundItems = itemSpringDataJpaRepository.findAll()
        return foundItems.map { itemJpaMapper.toDomain(it) }
    }

    override fun deleteById(id: Long): Boolean {
        return itemSpringDataJpaRepository.softDeleteById(id) > 0
    }
}