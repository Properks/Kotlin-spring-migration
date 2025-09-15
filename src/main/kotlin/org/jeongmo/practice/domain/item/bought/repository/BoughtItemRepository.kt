package org.jeongmo.practice.domain.item.bought.repository

import org.jeongmo.practice.domain.item.bought.entity.BoughtItem
import org.jeongmo.practice.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface BoughtItemRepository: JpaRepository<BoughtItem, Long> {
    fun findByMember(member: Member): List<BoughtItem>
    fun findByMemberAndId(member: Member, id: Long): BoughtItem?
    fun existsByMemberAndId(member: Member, id: Long): Boolean
}