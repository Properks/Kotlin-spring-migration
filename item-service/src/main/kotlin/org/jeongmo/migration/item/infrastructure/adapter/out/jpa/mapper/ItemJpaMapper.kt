package org.jeongmo.migration.item.infrastructure.adapter.out.jpa.mapper

import org.jeongmo.migration.item.domain.model.Item
import org.jeongmo.migration.item.infrastructure.adapter.out.jpa.domain.ItemJpaEntity
import org.springframework.stereotype.Component

@Component
class ItemJpaMapper {

    fun fromDomain(item: Item): ItemJpaEntity {
        return ItemJpaEntity(
            id = item.id,
            name = item.name,
            price = item.price,
            discount = item.discount,
            score = item.score,
            itemStatus = item.itemStatus,
            deletedAt = item.deletedAt,
        ).apply {
            this.createdAt = item.createdAt
            this.updatedAt = item.updatedAt
        }
    }

    fun toDomain(itemJpaEntity: ItemJpaEntity): Item =
        Item(
            id = itemJpaEntity.id,
            name = itemJpaEntity.name,
            price = itemJpaEntity.price,
            discount = itemJpaEntity.discount,
            score =  itemJpaEntity.score,
            itemStatus = itemJpaEntity.itemStatus,
            deletedAt = itemJpaEntity.deletedAt,
            createdAt = itemJpaEntity.createdAt,
            updatedAt = itemJpaEntity.updatedAt,
        )
}