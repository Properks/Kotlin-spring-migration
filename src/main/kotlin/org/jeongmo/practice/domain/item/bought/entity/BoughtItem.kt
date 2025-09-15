package org.jeongmo.practice.domain.item.bought.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.jeongmo.practice.domain.item.entity.Item
import org.jeongmo.practice.domain.item.bought.entity.enums.BoughtStatus
import org.jeongmo.practice.domain.member.entity.Member
import org.jeongmo.practice.global.common.entity.BaseEntity
import java.time.LocalDateTime

@Entity
@SQLDelete(sql = "UPDATE bought_item SET deleted_at = NOW() WHERE bought_item_id = ?")
@SQLRestriction("deleted_at IS NULL")
class BoughtItem(

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status : BoughtStatus,

    @Column(name = "quantity")
    val quantity: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item : Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member : Member,
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bought_item_id")
    val id : Long = 0

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
}