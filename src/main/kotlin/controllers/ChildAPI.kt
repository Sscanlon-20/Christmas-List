package controllers

import models.Child
import models.Gift
import utils.Utilities.formatListString
import utils.ValidateInput.isValidListIndex

class ChildAPI() {

    private var children = ArrayList<Child>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun add(child: Child): Boolean {
        child.childId = getId()
        return children.add(child)
    }

    fun delete(id: Int) = children.removeIf { child -> child.childId == id }

    fun updateChild(id: Int, child: Child?): Boolean {
        val foundChild = findChild(id)
        if ((foundChild != null) && (child != null)) {
            foundChild.childName = child.childName
            foundChild.childGender = child.childGender
            foundChild.childAge = child.childAge
            foundChild.behaviour = child.behaviour
            foundChild.totalAmount = child.totalAmount
            return true
        }
        return false
    }

    fun updateGift(childId: Int, id: Int, gift: Gift?): Boolean {
        val foundChild = findChild(childId)
        val foundGift = foundChild?.findOne(id)
        if ((foundGift != null) && (gift != null)) {
            foundGift.giftName = gift.giftName
            foundGift.cost = gift.cost
            foundGift.whereToBuy = gift.whereToBuy
            foundGift.category = gift.category
            foundGift.minAge = gift.minAge
            foundGift.recommendedGender = gift.recommendedGender
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR NOTE ArrayList
    // ----------------------------------------------

    fun listAllChildren() =
        if (children.isEmpty()) "No children stored"
        else formatListString(children)

    fun numberOfChildren() = children.size


    fun searchChildrenByName(searchString: String) =
        formatListString(
            children.filter { child -> child.childName.contains(searchString, ignoreCase = true) }
        )

    fun listByGender(gender: Char): String
    {
        return if (children.isEmpty()) "No children stored"
        else {
            val listOfChildren = formatListString(children.filter { child ->
                child.childGender == gender
            })
            if (listOfChildren.equals("")) "No children of this gender: $gender"
            else "${ numberOfChildrenByGender(gender) } $gender children: $listOfChildren"
        }
    }
    fun numberOfChildrenByGender(gender: Char): Int = children.count { p: Child -> p.childGender == gender }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, children)
    }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findChild(childId : Int) =  children.find{ it.childId == childId}
    fun searchChildByName(searchString: String) =
        formatListString(
            children.filter { child -> child.childName.contains(searchString, ignoreCase = true) }
        )

    fun searchGiftByContents(searchString: String): String {
        return if (numberOfChildren() == 0) "No notes stored"
        else {
            var listOfChildren = ""
            for (child in children) {
                for (gift in child.gifts) {
                    if (gift.giftName.contains(searchString, ignoreCase = true)) {
                        listOfChildren += "${child.childId}: ${child.childName} \n\t${gift}\n"
                    }
                }
            }
            if (listOfChildren == "") "No items found for: $searchString"
            else listOfChildren
        }
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR ITEMS
    // ----------------------------------------------
    fun listGifts(): String { //todo why is it greyed out - fix
        return if (numberOfChildren() == 0) "No children stored"
        else {
            var listGifts = ""
            for (child in children) {
                for (gift in child.gifts) {
                    listGifts += child.childName + ": " + gift.giftName + "\n"
                }
            }
            listGifts
        }
    }

    fun listChildrenOver3(): String {
        return if (children.isEmpty()) "No children stored"
        else {
            val listOfChildren = formatListString(children.filter { child ->
                child.childAge >=3
            })
            if (listOfChildren.equals("")) "No children over 3"
            else "$   $listOfChildren"
        }
    }

    //todo fix
    @Throws(Exception::class)
    fun load() {
        val serializer = null
        children = serializer.read() as ArrayList<Child>
    }

    @Throws(Exception::class)
    fun store() {
        val serializer = null
        serializer.write(children)
    }


}