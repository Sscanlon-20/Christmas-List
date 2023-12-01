package controllers

import models.Child
import models.Gift
import persistance.Serializer
import utils.Utilities.formatListString
import utils.ValidateInput.isValidListIndex

class ChildAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
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


    fun searchChildByName(searchString: String) =
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

    @Throws(Exception::class)
    fun load() {
        children = serializer.read() as ArrayList<Child>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(children)
    }


}