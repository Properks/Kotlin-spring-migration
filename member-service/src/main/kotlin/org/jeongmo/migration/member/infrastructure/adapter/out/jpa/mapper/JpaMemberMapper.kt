package org.jeongmo.migration.member.infrastructure.adapter.out.jpa.mapper

import org.jeongmo.migration.member.domain.model.Member
import org.jeongmo.migration.member.infrastructure.adapter.out.jpa.domain.MemberJpaEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class JpaMemberMapper {

    fun fromDomain(member: Member): MemberJpaEntity {
        return MemberJpaEntity(
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

    fun toDomain(memberJpaEntity: MemberJpaEntity): Member {
        return Member(
            id = memberJpaEntity.id,
            username = memberJpaEntity.username,
            password = memberJpaEntity.password,
            providerType = memberJpaEntity.providerType,
            nickname = memberJpaEntity.nickname,
            role = memberJpaEntity.role,
            deletedAt = memberJpaEntity.deletedAt,
            createdAt = memberJpaEntity.createdAt,
            updatedAt = memberJpaEntity.updatedAt,
        )
    }
}