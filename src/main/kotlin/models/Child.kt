package models

import utils.Utilities

data class Child(
    var childId: Int = 0,
    var childName: String,
    var childGender: Char = 'n',
    var childAge: Int = 0,
    var gifts: MutableSet<Gift> = mutableSetOf()
) {

    private var lastGiftId = 0
    private fun getGiftId() = lastGiftId++

    fun addGift(gift: Gift): Boolean {
        gift.giftId = getGiftId()
        return gifts.add(gift)
    }

    fun numberOfGifts() = gifts.size

    fun findOne(id: Int): Gift? {
        return gifts.find { gift -> gift.giftId == id }
    }

    fun delete(id: Int): Boolean {
        return gifts.removeIf { gift -> gift.giftId == id }
    }

    fun getCostOfList(): Int {
        var totalCost = 0
        for (gift in gifts) {
            totalCost += gift.cost
        }
        return totalCost
    }

    fun listGifts() =
        if (gifts.isEmpty()) {
            "\tNO GIFTS ADDED"
        } else {
            Utilities.formatSetString(gifts)
        }

    override fun toString(): String {
        return "Child(childId=$childId, childName='$childName', childGender=$childGender, childAge=$childAge)"
    }
}
