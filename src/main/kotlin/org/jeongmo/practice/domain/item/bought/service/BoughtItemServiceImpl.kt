package org.jeongmo.practice.domain.item.bought.service

import org.jeongmo.practice.domain.item.bought.dto.BuyItemRequest
import org.jeongmo.practice.domain.item.bought.dto.UpdateBoughtItemStatusRequest
import org.jeongmo.practice.domain.item.bought.entity.BoughtItem
import org.jeongmo.practice.domain.item.bought.entity.enums.BoughtStatus
import org.jeongmo.practice.domain.item.bought.repository.BoughtItemRepository
import org.jeongmo.practice.domain.item.entity.Item
import org.jeongmo.practice.domain.item.repository.ItemRepository
import org.jeongmo.practice.domain.member.entity.Member
import org.jeongmo.practice.domain.member.repository.MemberRepository
import org.jeongmo.practice.global.error.code.BoughtItemErrorCode
import org.jeongmo.practice.global.error.code.ItemErrorCode
import org.jeongmo.practice.global.error.code.MemberErrorCode
import org.jeongmo.practice.global.error.exception.BoughtItemException
import org.jeongmo.practice.global.error.exception.ItemException
import org.jeongmo.practice.global.error.exception.MemberException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BoughtItemServiceImpl(
    private val boughtItemRepository: BoughtItemRepository,
    private val itemRepository: ItemRepository,
    private val memberRepository: MemberRepository,
): BoughtItemService {

    override fun buyItem(username: String, request: BuyItemRequest): BoughtItem {
        buyProcessing(username, request)
        val item: Item = getItem(request.boughtItemId)
        val member: Member = getMember(username)
        val boughtItem: BoughtItem = BoughtItem(BoughtStatus.ACCEPTING, request.quantity, item, member)
        return boughtItemRepository.save(boughtItem)
    }

    @Transactional(readOnly = true)
    override fun findBoughtItems(username: String): List<BoughtItem> {
        val member: Member = getMember(username)
        return boughtItemRepository.findByMember(member)
    }

    @Transactional(readOnly = true)
    override fun findBoughtItem(username: String, boughtItemId: Long): BoughtItem {
        val member: Member = getMember(username)
        return boughtItemRepository.findByMemberAndId(member, boughtItemId) ?: throw BoughtItemException(BoughtItemErrorCode.NOT_FOUND)
    }

    override fun cancelItem(username: String, boughtItemId: Long) {
        val member: Member = getMember(username)
        if (!isOwn(member, boughtItemId)) {
            throw BoughtItemException(BoughtItemErrorCode.NOT_YOURS)
        }
        boughtItemRepository.deleteById(boughtItemId)
    }

    override fun updateStatus(boughtItemId: Long, request: UpdateBoughtItemStatusRequest): BoughtItem{
        val foundBoughtItem: BoughtItem = boughtItemRepository.findById(boughtItemId).orElseThrow { throw BoughtItemException(BoughtItemErrorCode.NOT_FOUND) }
        foundBoughtItem.status = request.status
        return foundBoughtItem
    }

    private fun buyProcessing(username: String, request: BuyItemRequest) {
        // TODO
    }

    private fun getMember(username: String): Member {
        return memberRepository.findByUsername(username) ?: throw MemberException(MemberErrorCode.NOT_FOUND)
    }

    private fun getItem(itemId: Long): Item {
        return itemRepository.findById(itemId).orElseThrow { throw ItemException(ItemErrorCode.NOT_FOUND) }
    }

    private fun isOwn(member: Member, boughtItemId: Long): Boolean {
        return boughtItemRepository.existsByMemberAndId(member, boughtItemId)
    }
}