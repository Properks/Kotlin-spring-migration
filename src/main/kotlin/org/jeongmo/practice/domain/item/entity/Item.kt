package org.jeongmo.practice.domain.item.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.jeongmo.practice.global.common.entity.BaseEntity

@Entity
class Item(

    @Column(name = "name")
    var name : String,

    @Column(name = "price")
    var price : Long,

    @Column(name = "discount")
    var discount : Double = 0.0,

    @Column(name = "discountPrice")
    var discountPrice : Long?,

    @Column(name = "score")
    var score : Double,

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long = 0

}