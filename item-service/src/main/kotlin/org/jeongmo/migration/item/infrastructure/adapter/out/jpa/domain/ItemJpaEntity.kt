package org.jeongmo.migration.item.infrastructure.adapter.out.jpa.domain

import jakarta.persistence.*
import org.jeongmo.migration.common.domain.jpa.JpaBaseEntity
import org.jeongmo.migration.item.domain.enums.ItemStatus
import java.time.LocalDateTime

@Entity
class ItemJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long = 0L,

    @Column(name = "name")
    var name : String,

    @Column(name = "price")
    var price : Long,

    @Column(name = "discount")
    var discount : Double = 0.0,

    @Column(name = "score")
    var score : Double? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status")
    var itemStatus: ItemStatus = ItemStatus.IN_STOCK,

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null,
): JpaBaseEntity() {
}