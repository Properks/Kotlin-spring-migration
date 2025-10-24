package org.jeongmo.migration.member.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.member.infrastructure.adapter.out.jpa.domain.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberSpringDataJpaRepository: JpaRepository<MemberJpaEntity, Long> {
}