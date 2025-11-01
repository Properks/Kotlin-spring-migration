package org.jeongmo.migration.member.domain.repository

import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.member.domain.model.Member

interface MemberRepository {

    fun save(member: Member): Member
    fun findById(id: Long): Member?
    fun findByUsernameAndProviderType(username: String, providerType: ProviderType): Member?
    fun findAll(): List<Member>
    fun delete(id: Long): Boolean
}