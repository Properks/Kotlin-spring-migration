package org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.mapper

import org.jeongmo.migration.bought.item.domain.model.BoughtItem
import org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.entity.BoughtItemJpaEntity
import org.springframework.stereotype.Component

@Component
class BoughtItemJpaMapper {
    fun fromDomain(domain: BoughtItem): BoughtItemJpaEntity {
        return BoughtItemJpaEntity(
            id = domain.id,
            quantity = domain.quantity,
            memberId = domain.memberId,
            itemId = domain.itemId,
            boughtStatus = domain.boughtStatus,
            deletedAt = domain.deletedAt,
        )
    }

    fun toDomain(entity: BoughtItemJpaEntity): BoughtItem {
        return BoughtItem(
            id = entity.id,
            quantity = entity.quantity,
            memberId = entity.memberId,
            itemId = entity.itemId,
            boughtStatus = entity.boughtStatus,
            deletedAt = entity.deletedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
        )
    }
}