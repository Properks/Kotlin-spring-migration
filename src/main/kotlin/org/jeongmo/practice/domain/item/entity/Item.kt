package org.jeongmo.practice.domain.item.entity

import jakarta.persistence.*
import org.jeongmo.practice.domain.item.entity.enums.ItemStatus
import org.jeongmo.practice.global.common.entity.BaseEntity

@Entity
class Item(

    @Column(name = "name")
    var name : String,

    @Column(name = "price")
    var price : Long,

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long = 0

    @Column(name = "discount")
    var discount : Double = 0.0

    @Column(name = "discountPrice")
    var discountPrice : Long? = null

    @Column(name = "score")
    var score : Double = 0.0

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status")
    var itemStatus: ItemStatus = ItemStatus.IN_STOCK
}