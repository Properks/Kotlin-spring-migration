package org.jeongmo.practice.domain.item.options.service

import org.jeongmo.practice.domain.item.entity.Item
import org.jeongmo.practice.domain.item.options.converter.toEntity
import org.jeongmo.practice.domain.item.options.dto.CreateItemOptionRequest
import org.jeongmo.practice.domain.item.options.dto.UpdateItemOptionRequest
import org.jeongmo.practice.domain.item.options.entity.ItemOption
import org.jeongmo.practice.domain.item.options.repository.ItemOptionRepository
import org.jeongmo.practice.domain.item.repository.ItemRepository
import org.jeongmo.practice.global.error.code.ItemErrorCode
import org.jeongmo.practice.global.error.code.ItemOptionErrorCode
import org.jeongmo.practice.global.error.exception.ItemException
import org.jeongmo.practice.global.error.exception.ItemOptionException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemOptionServiceImpl(
    private val itemRepository: ItemRepository,
    private val itemOptionRepository: ItemOptionRepository,
): ItemOptionService {

    override fun createItemOption(itemId: Long, request: CreateItemOptionRequest): ItemOption {
        val item: Item = findItem(itemId)
        return itemOptionRepository.save(request.toEntity(item))
    }

    @Transactional(readOnly = true)
    override fun findItemOptions(itemId: Long): List<ItemOption> = itemOptionRepository.findByItemId(itemId)

    @Transactional(readOnly = true)
    override fun findItemOption(itemOptionId: Long): ItemOption = itemOptionRepository.findById(itemOptionId).orElseThrow { ItemOptionException(ItemOptionErrorCode.NOT_FOUND) }

    override fun updateItemOption(itemOptionId: Long, request: UpdateItemOptionRequest): ItemOption {
        val itemOption: ItemOption = itemOptionRepository.findById(itemOptionId).orElseThrow { ItemOptionException(ItemOptionErrorCode.NOT_FOUND) }
        itemOption.optionName = request.optionName ?: itemOption.optionName
        itemOption.additionalPrice = request.additionalPrice ?: itemOption.additionalPrice
        return itemOption
    }

    override fun deleteItemOption(itemOptionId: Long) = itemOptionRepository.deleteById(itemOptionId)

    private fun findItem(itemId: Long): Item = itemRepository.findById(itemId).orElseThrow { ItemException(ItemErrorCode.NOT_FOUND) }
}