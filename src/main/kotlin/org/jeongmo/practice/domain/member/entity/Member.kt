package org.jeongmo.practice.domain.member.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.jeongmo.practice.domain.member.entity.enums.ProviderType
import org.jeongmo.practice.domain.member.entity.enums.Role
import org.jeongmo.practice.global.common.entity.BaseEntity
import java.time.LocalDateTime

@Entity
@SQLDelete(sql = "UPDATE member SET deleted_at = now() where member_id = ?")
@SQLRestriction("deleted_at IS NULL")
class Member(

    @Column(name = "username", unique = true)
    var username : String,

    @Column(name = "password")
    var password : String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type")
    var providerType : ProviderType,

    @Column(name = "nickname")
    var nickname : String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role : Role,

    @Column(name = "deleted_at")
    var deletedAt : LocalDateTime?,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id : Long = 0

}