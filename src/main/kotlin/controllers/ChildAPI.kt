package controllers

import models.Child
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

    fun deleteChild(indexToDelete: Int): Child? {
        return if (isValidListIndex(indexToDelete, children)) {
            children.removeAt(indexToDelete)
        } else null
    }

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

    // ----------------------------------------------
    //  LISTING METHODS FOR NOTE ArrayList
    // ----------------------------------------------

    fun listAllChildren() =
        if (children.isEmpty()) "No children stored"
        else formatListString(children)

    fun numberOfChildren() = children.size

    fun findChild(childId: Int) = children.find { child -> child.childId == childId }

    fun searchChildrenByName(searchString: String) =
        formatListString(
            children.filter { child -> child.childName.contains(searchString, ignoreCase = true) }
        )


    //TODO Fix gender
    fun listByGender() (childGender: Int): String =
    if (child.isEmpty()) “No children stored”
    else
    {
        val listOfChildren = formatListString(children.filter { child ->
            val gender
            child.childGender == gender
        })
        if (listOfChildren.equals(“”)) "No children of this gender: $gender"
        else “${ numberOfChildrenByGender(gender) } "Number of $gender children: $listOfChildren”
    }
    fun numberOfChildrenByGender(gender: Int): Int = children.count { p: Child -> p.childGender == gender }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, children)
    }



}