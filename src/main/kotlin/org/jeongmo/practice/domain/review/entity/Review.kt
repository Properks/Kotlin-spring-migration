package org.jeongmo.practice.domain.review.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.jeongmo.practice.domain.item.bought.entity.BoughtItem
import org.jeongmo.practice.global.common.entity.BaseEntity
import java.time.LocalDateTime

@Entity
@SQLDelete(sql = "UPDATE review SET deleted_at = NOW() WHERE review_id = ?")
@SQLRestriction("deleted_at IS NULL")
class Review (

    @Column(name = "score")
    var score : Double,

    @Column(name = "content", columnDefinition = "TEXT")
    var content : String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bought_item_id")
    val boughtItem : BoughtItem
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    val id : Long = 0

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
}