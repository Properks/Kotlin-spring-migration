package org.jeongmo.migration.member.infrastructure.adapter.out.jpa.domain

import jakarta.persistence.*
import org.jeongmo.migration.common.domain.jpa.JpaBaseEntity
import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.common.enums.member.Role
import java.time.LocalDateTime

@Entity
@Table(
    name = "member",
    uniqueConstraints = [UniqueConstraint(columnNames = ["username", "provider_type"])],
)
class MemberJpaEntity(

    @Column(name = "username")
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0L
}