package org.jeongmo.practice.domain.item.entity

import jakarta.persistence.*
import org.jeongmo.practice.global.common.entity.BaseEntity

@Entity
class ItemOption(

    @Column(name = "option_name")
    var optionName : String,

    @Column(name = "additional_price")
    var additionalPrice : Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item : Item,

): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_option_id")
    val id : Long = 0

}