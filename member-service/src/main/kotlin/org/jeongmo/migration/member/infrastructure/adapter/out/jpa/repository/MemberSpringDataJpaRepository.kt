package org.jeongmo.migration.member.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.member.domain.enum.ProviderType
import org.jeongmo.migration.member.infrastructure.adapter.out.jpa.domain.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberSpringDataJpaRepository: JpaRepository<MemberJpaEntity, Long> {
    fun findByUsernameAndProviderType(username: String, providerType: ProviderType): MemberJpaEntity?
}