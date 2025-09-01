package org.jeongmo.practice.domain.item.options.repository

import org.jeongmo.practice.domain.item.options.entity.ItemOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemOptionRepository: JpaRepository<ItemOption, Long> {
    @Query("SELECT io FROM ItemOption io WHERE io.item.id = :itemId")
    fun findByItemId(@Param("itemId") itemId: Long): List<ItemOption>
}