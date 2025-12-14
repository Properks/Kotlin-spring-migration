package org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.bought.item.domain.model.BoughtItem
import org.jeongmo.migration.bought.item.domain.repository.BoughtItemRepository
import org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.mapper.BoughtItemJpaMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class BoughtItemJpaRepository(
    private val boughtItemSpringDataJpaRepository: BoughtItemSpringDataJpaRepository,
    private val boughtItemJpaMapper: BoughtItemJpaMapper,
): BoughtItemRepository {

    @Transactional
    override fun save(boughtItem: BoughtItem): BoughtItem {
        val savedBoughtItem = boughtItemSpringDataJpaRepository.save(boughtItemJpaMapper.fromDomain(boughtItem))
        return boughtItemJpaMapper.toDomain(savedBoughtItem)
    }

    @Transactional(readOnly = true)
    override fun findById(ownerId: Long, id: Long): BoughtItem? {
        val foundBoughtItem = boughtItemSpringDataJpaRepository.findByIdAndMemberId(id, ownerId)
        return foundBoughtItem?.let {
            boughtItemJpaMapper.toDomain(it)
        }
    }

    @Transactional(readOnly = true)
    override fun findAll(ownerId: Long): List<BoughtItem> {
        return boughtItemSpringDataJpaRepository.findAllByMemberId(ownerId).map { boughtItemJpaMapper.toDomain(it) }
    }

    @Transactional
    override fun delete(ownerId: Long, id: Long): Boolean {
        return boughtItemSpringDataJpaRepository.softDeleteBoughtItem(id, ownerId) > 0
    }
}