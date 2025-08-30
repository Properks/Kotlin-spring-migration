package org.jeongmo.practice.domain.item.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.jeongmo.practice.domain.item.entity.enums.ItemStatus
import org.jeongmo.practice.global.common.entity.BaseEntity
import java.time.LocalDateTime

@Entity
@SQLDelete(sql = "UPDATE item SET deleted_at = now() WHERE item_id = ?")
@SQLRestriction("deleted_at IS NULL")
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
        set(discount) {
            field = discount
            discountPrice = if (discount == 0.0) null else (price * (1 - discount)).toLong()
        }

    @Column(name = "discountPrice")
    var discountPrice : Long? = null
    protected set

    @Column(name = "score")
    var score : Double = 0.0

    @Enumerated(EnumType.STRING)
    @Column(name = "item_status")
    var itemStatus: ItemStatus = ItemStatus.IN_STOCK

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null
}