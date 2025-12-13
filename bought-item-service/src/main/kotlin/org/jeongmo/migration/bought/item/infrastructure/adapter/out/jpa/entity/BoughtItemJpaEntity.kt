package org.jeongmo.migration.bought.item.infrastructure.adapter.out.jpa.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.jeongmo.migration.common.domain.jpa.JpaBaseEntity
import org.jeongmo.practice.domain.item.bought.entity.enums.BoughtStatus
import java.time.LocalDateTime

@Entity
@Table(name = "bought_item")
@SQLDelete(sql = "UPDATE bought_item SET bought_item.deleted_at = now() WHERE bought_item.bought_item_id = ?")
@SQLRestriction("deleted_at IS NULL")
class BoughtItemJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bought_item_id")
    val id: Long = 0L,

    @Column(name = "quantity")
    var quantity: Long,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "item_id")
    val itemId: Long,

    @Column(name = "bought_status")
    @Enumerated(EnumType.STRING)
    var boughtStatus: BoughtStatus,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,

): JpaBaseEntity() {
}