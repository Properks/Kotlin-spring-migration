package org.jeongmo.migration.item.application.service

import org.jeongmo.migration.item.application.dto.*
import org.jeongmo.migration.item.application.port.inbound.ItemCommandUseCase
import org.jeongmo.migration.item.application.port.inbound.ItemQueryUseCase
import org.jeongmo.migration.item.domain.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository,
): ItemQueryUseCase, ItemCommandUseCase {

    override fun createItem(request: CreateItemRequest): CreateItemResponse {
        val item = request.toDomain()
        return CreateItemResponse.fromDomain(itemRepository.save(item))
    }

    override fun findById(id: Long): ItemInfoResponse {
        val item = itemRepository.findById(id)
        return ItemInfoResponse.fromDomain(item)
    }

    override fun findAll(): List<ItemInfoResponse> {
        return itemRepository.findAll()
            .map {
                ItemInfoResponse.fromDomain(it)
            }
    }

    override fun updateItem(id: Long, request: UpdateItemRequest): UpdateItemResponse {
        val item = itemRepository.findById(id)
        request.name?.let { item.changeName(it) }
        request.price?.let { item.changePrice(it) }
        request.discount?.let { item.changeDiscount(it) }
        request.itemStatus?.let { item.changeItemStatus(it) }
        return UpdateItemResponse.fromDomain(itemRepository.save(item))
    }

    override fun deleteItem(id: Long) {
        itemRepository.deleteById(id)
    }
}