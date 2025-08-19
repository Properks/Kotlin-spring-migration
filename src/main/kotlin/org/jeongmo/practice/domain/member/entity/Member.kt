package org.jeongmo.practice.domain.member.entity

import jakarta.persistence.*
import org.jeongmo.practice.domain.member.entity.enums.ProviderType
import org.jeongmo.practice.domain.member.entity.enums.Role
import org.jeongmo.practice.global.common.entity.BaseEntity
import java.time.LocalDateTime

@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id : Long?,

    @Column(name = "username", unique = true)
    var username : String,

    @Column(name = "password")
    var password : String?,

    @Column(name = "provider_type")
    var providerType : ProviderType,

    @Column(name = "nickname")
    var nickname : String,

    @Column(name = "role")
    val role : Role,

    @Column(name = "deleted_at")
    var deletedAt : LocalDateTime?,
) : BaseEntity() {
}