package org.jeongmo.practice.domain.item.entity

import jakarta.persistence.*
import org.jeongmo.practice.domain.item.entity.enums.BoughtStatus
import org.jeongmo.practice.domain.member.entity.Member

@Entity
class BoughtItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bought_item_id")
    var id : Long?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status : BoughtStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item : Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member : Member,
) {
}