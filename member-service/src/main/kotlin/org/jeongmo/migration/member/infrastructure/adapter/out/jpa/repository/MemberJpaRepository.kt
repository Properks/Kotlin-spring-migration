package org.jeongmo.migration.member.infrastructure.adapter.out.jpa.repository

import org.jeongmo.migration.member.domain.enum.ProviderType
import org.jeongmo.migration.member.domain.model.Member
import org.jeongmo.migration.member.domain.repository.MemberRepository
import org.jeongmo.migration.member.infrastructure.adapter.out.jpa.mapper.JpaMemberMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class MemberJpaRepository(
    private val jpaRepository: MemberSpringDataJpaRepository,
    private val mapper: JpaMemberMapper,
): MemberRepository {

    override fun save(member: Member): Member {
        val savedEntity = jpaRepository.save(mapper.fromDomain(member))
        return mapper.toDomain(savedEntity)
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): Member? {
        val foundEntity = jpaRepository.findById(id).orElse(null)
        return if (foundEntity == null) null else mapper.toDomain(foundEntity)
    }

    @Transactional(readOnly = true)
    override fun findByUsernameAndProviderType(username: String, providerType: ProviderType): Member? {
        val foundEntity = jpaRepository.findByUsernameAndProviderType(username, providerType)
        return foundEntity?.let { mapper.toDomain(it) }
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<Member> {
        return jpaRepository.findAll().map {mapper.toDomain(it)}
    }

    override fun delete(id: Long): Boolean {
        var exist = jpaRepository.existsById(id)
        if (!exist) return false
        jpaRepository.deleteById(id)
        return true
    }
}