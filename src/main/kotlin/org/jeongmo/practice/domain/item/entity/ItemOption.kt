package org.jeongmo.practice.domain.item.entity

import jakarta.persistence.*

@Entity
class ItemOption(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_option_id")
    var id : Long?,

    @Column(name = "option_name")
    var optionName : String,

    @Column(name = "additional_price")
    var additionalPrice : Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item : Item,

) {
}