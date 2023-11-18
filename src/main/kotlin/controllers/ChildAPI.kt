package controllers

import models.Child
import utils.Utilities.formatListString
import java.util.ArrayList

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

    fun update(id: Int, child: Child?): Boolean {

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
//HOW TO DO THIS???????
    // ----------------------------------------------
    //  LISTING METHODS FOR NOTE ArrayList
    // ----------------------------------------------

    fun listAllChildren() =
        if (children.isEmpty()) "No children stored"
        else formatListString(children)

    fun numberOfChildren() = children.size

    fun findChild(childId : Int) =  children.find{ child -> child.childId == childId }

    fun searchChildrenByName(searchString: String) =
        formatListString(
            children.filter { child -> child.childName.contains(searchString, ignoreCase = true) }
        )

    // ----------------------------------------------
    //  LISTING METHODS FOR ITEMS
    // ----------------------------------------------
    fun listTodoItems(): String =
        if (numberOfNotes() == 0) "No notes stored"
        else {
            var listOfTodoItems = ""
            for (note in notes) {
                for (item in note.items) {
                    if (!item.isItemComplete) {
                        listOfTodoItems += note.noteTitle + ": " + item.itemContents + "\n"
                    }
                }
            }
            listOfTodoItems
        }

    // ----------------------------------------------
    //  COUNTING METHODS FOR ITEMS
    // ----------------------------------------------
    fun numberOfToDoItems(): Int {
        var numberOfToDoItems = 0
        for (note in notes) {
            for (item in note.items) {
                if (!item.isItemComplete) {
                    numberOfToDoItems++
                }
            }
        }
        return numberOfToDoItems
    }

}