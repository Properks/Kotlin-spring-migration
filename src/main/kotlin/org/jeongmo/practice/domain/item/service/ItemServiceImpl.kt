package org.jeongmo.practice.domain.item.service

import org.jeongmo.practice.domain.item.converter.toEntity
import org.jeongmo.practice.domain.item.dto.RegisterItemRequest
import org.jeongmo.practice.domain.item.dto.UpdateItemsRequest
import org.jeongmo.practice.domain.item.entity.Item
import org.jeongmo.practice.domain.item.repository.ItemRepository
import org.jeongmo.practice.global.error.code.ItemErrorCode
import org.jeongmo.practice.global.error.exception.ItemException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemServiceImpl(
    private val itemRepository: ItemRepository,
): ItemService {

    override fun registerItem(request: RegisterItemRequest): Item {
        val item: Item = request.toEntity()
        item.discount = request.discount ?: 0.0
        return itemRepository.save(item)
    }

    @Transactional(readOnly = true)
    override fun findItems(): List<Item> {
        return itemRepository.findAll()
    }

    @Transactional(readOnly = true)
    override fun findItem(id: Long): Item {
        return itemRepository.findById(id).orElseThrow { throw ItemException(ItemErrorCode.NOT_FOUND) }
    }

    override fun updateItem(id: Long, request: UpdateItemsRequest): Item {
        val foundItem: Item = itemRepository.findById(id).orElseThrow { throw ItemException(ItemErrorCode.NOT_FOUND) }
        foundItem.name = request.name
        foundItem.price = request.price
        foundItem.discount = request.discount ?: foundItem.discount
        return foundItem
    }

    override fun deleteItem(id: Long) = itemRepository.deleteById(id)
}