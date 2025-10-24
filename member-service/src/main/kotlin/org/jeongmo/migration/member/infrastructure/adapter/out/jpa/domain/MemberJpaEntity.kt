package org.jeongmo.migration.member.infrastructure.adapter.out.jpa.domain

import jakarta.persistence.*
import org.jeongmo.migration.common.domain.jpa.JpaBaseEntity
import org.jeongmo.migration.member.domain.enum.ProviderType
import org.jeongmo.migration.member.domain.enum.Role
import org.jeongmo.migration.member.domain.model.Member
import java.time.LocalDateTime

@Entity
@Table(name = "member")
class MemberJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long,

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
): JpaBaseEntity() {

    companion object {
        fun fromDomain(member: Member): MemberJpaEntity = MemberJpaEntity(
            id = member.id ?: 0L,
            username = member.username,
            password = member.password,
            providerType = member.providerType,
            nickname = member.nickname,
            role = member.role,
            deletedAt = member.deletedAt,
        ).apply {
            this.createdAt = member.createdAt ?: LocalDateTime.now()
            this.updatedAt = member.updatedAt ?: LocalDateTime.now()
        }
    }
}