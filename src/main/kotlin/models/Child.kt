package models

import utils.Utilities

data class Child(var childId: Int = 0,
                 var childName: String,
                 var childGender: Char = 'n',
                 var childAge: Int = 0,
                 var behaviour: Boolean = false,
                 var totalAmount: Int = 0,
                 var gifts : MutableSet<Gift> = mutableSetOf()) {


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

// HOW TO DO THIS PART???????
    fun checkChildCompletionStatus(): Boolean {
        if (gifts.isNotEmpty()) {
            for (gift in gifts) {
                if (!gift.isGiftComplete) {
                    return false
                }
            }
        }
        return true //a note with empty items can be archived, or all items are complete
    }


    fun GiftItems() =
        if (gifts.isEmpty())  "\tNO GIFTS ADDED"
        else  Utilities.formatSetString(gifts)

    override fun toString(): String {
        val archived = if (isNoteArchived) 'Y' else 'N'
        return "${giftId}Id: $giftName ($cost) $whereToBuy [$category] $minAge $recommendedGender"
    }

}








