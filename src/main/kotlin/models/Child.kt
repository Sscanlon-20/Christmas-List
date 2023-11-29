package models

import utils.Utilities

data class Child(
    var childId: Int = 0,
    var childName: String,
    var childGender: Char = 'n',
    var childAge: Int = 0,
    var behaviour: Boolean = false,
    var totalAmount: Int = 0,
    var gifts: MutableSet<Gift> = mutableSetOf()) {


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

    fun update(id: Int, newGift: Gift): Boolean {
        val foundGift = findOne(id)
        if (foundGift != null){
            foundGift.giftName = newGift.giftName
            foundGift.cost = newGift.cost
            foundGift.whereToBuy = newGift.whereToBuy
            foundGift.category = newGift.category
            foundGift.minAge = newGift.minAge
            foundGift.recommendedGender = newGift.recommendedGender
            return true
        }
        return false
    }


    fun getCostOfList(): Int {
        var totalCost = 0
            for (gift in gifts) {
                totalCost += gift.cost
            }

        return totalCost
    }

    fun GiftItems() =
        if (gifts.isEmpty())  "\tNO GIFTS ADDED"
        else  Utilities.formatSetString(gifts)

    override fun toString(): String {
        return "Child(childId=$childId, childName='$childName', childGender=$childGender, childAge=$childAge, " +
                "behaviour=$behaviour, totalAmount=$totalAmount)"
    }


}








