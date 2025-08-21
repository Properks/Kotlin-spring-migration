package org.jeongmo.practice.domain.member.repository

import org.jeongmo.practice.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByUsername(username: String): Member?
}