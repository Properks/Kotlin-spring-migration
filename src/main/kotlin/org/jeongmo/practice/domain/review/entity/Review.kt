package org.jeongmo.practice.domain.review.entity

import jakarta.persistence.*
import org.jeongmo.practice.domain.item.entity.BoughtItem
import org.jeongmo.practice.global.common.entity.BaseEntity

@Entity
class Review (

    @Column(name = "score")
    var score : Double,

    @Column(name = "content", columnDefinition = "TEXT")
    var content : String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bought_item_id")
    var boughtItem : BoughtItem,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    val id : Long = 0

}