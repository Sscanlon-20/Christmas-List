import controllers.ChildAPI
import models.Child
import models.Gift
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput
import kotlin.system.exitProcess

private val childAPI = ChildAPI()

fun main() = runMenu()
fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addChild()
            2 -> listChildren()
            3 -> updateChild()
            4 -> deleteChild()

            5 -> addGiftToChild()
            6 -> updateGiftDetailsForChild()
            7 -> deleteGiftFromChild()

            8 -> searchChildByName()

            9 -> searchGifts()
            10 -> generateAgift()
            11 -> addOrRemoveGift()
            12 -> generateASuprise()

            13 -> save()
            14 -> load()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu(): Int {
    var readNextInt = readNextInt(
        """ 
             > --------------------------------------------------------- 
             > |          Christmas Shopping List                      |
             > ---------------------------------------------------------
             > | NOTE MENU                                             |
             > |   1) Add a child                                      |
             > |   2) List children                                    |
             > |   3) Update a child                                   |
             > |   4) Delete a child                                   |
             > ---------------------------------------------------------
             > | ITEM MENU                                             | 
             > |   6) Add gift to a child                              |
             > |   7) Update gift details for a child                  |
             > |   8) Delete a gift from child                         |
             > ---------------------------------------------------------
             > | REPORT MENU FOR NOTES                                 | 
             > |   9) Search for all children (by name)                |
             > --------------------------------------------------------- 
             > | REPORT MENU FOR ITEMS 
             >     10) Search Gift                                     |                                
             > |   10) Generate a gift                                 |
             > |   11) Add or remove gifts (depending on total amount) |
             > |   12) Generate a surprise (if child is good)          |
             > ---------------------------------------------------------
             > |   13) Save                                            |
             > |   14) Load                                            |
             > ---------------------------------------------------------
             > |   0) Exit                                             |
             > --------------------------------------------------------- 
             > ==>> """.trimMargin(">")
    )
    return readNextInt
}

//------------------------------------
//NOTE MENU
//------------------------------------

fun addChild() {
    val childName = readNextLine("Enter the child's name: ")
    val childGender = readNextChar("Enter gender (b or g): ")
    val childAge = readNextInt("Enter a child's ages: ")
    val behaviour = ValidateInput.readYN("Was the child good or bad (y/n)? ")
    val totalAmount = readNextInt("How much should the child's gifts amount to? ")

    val isAdded = childAPI.add(
        Child(
            childName = childName,
            childGender = childGender,
            childAge = childAge,
            behaviour = behaviour,
            totalAmount = totalAmount
        )
    )

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listChildren() {
    if (childAPI.numberOfChildren() > 0) {
        val option = readNextInt(
            """
                  > ----------------------------------
                  > |   1) List all children         |
                  > |   2) List children by gender   |
                  > |   3) List all children over 3  |
                  > ----------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllChildren()
            2 -> listByGender()
            3 -> listChildrenOver3()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllChildren() = println(childAPI.listAllChildren())
fun listByGender() = println(childAPI.listByGender()) //TODO fix
fun listChildrenOver3() = println(childAPI.listChildrenOver3()) //TODO how??

fun updateChild() {
    listChildren()
    if (childAPI.numberOfChildren() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the child to update: ")
        if (childAPI.isValidIndex(indexToUpdate)) {
            val childName = readNextLine("Enter the child's name: ")
            val childGender = readNextChar("Enter gender (b or g): ")
            val childAge = readNextInt("Enter a child's ages: ")
            val behaviour = ValidateInput.readYN("Was the child good or bad (y/n)? ")
            val totalAmount = readNextInt("How much should the child's gifts amount to? ")

            if (childAPI.updateChild(
                    indexToUpdate,
                    Child(0, childName, childGender, childAge, behaviour, totalAmount)
                )
            ) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no children with this index number")
        }
    }
}

fun deleteChild() {
    listChildren()
    val id = readNextInt("Enter the id of the child to delete: ")
    // pass the index of the note to NoteAPI for deleting and check for success.
    val childToDelete = childAPI.delete(id)
    if (childToDelete) {
        println("Delete Successful!")
    } else {
        println("Delete NOT Successful")
    }
}

//------------------------------------
//NOTE REPORTS MENU
//------------------------------------
fun searchChildByName() {
    val searchName = readNextLine("Enter the name to search by: ")
    val searchResults = childAPI.searchChildByName(searchName)
    if (searchResults.isEmpty()) {
        println("No children found")
    } else {
        println(searchResults)
    }
}

//------------------------------------
//ITEM REPORTS MENU
//------------------------------------
fun searchGifts() {
    val searchContents = readNextLine("Enter the gift contents to search by: ")
    val searchResults = childAPI.searchGiftByContents(searchContents)
    if (searchResults.isEmpty()) {
        println("No items found")
    } else {
        println(searchResults)
    }
}

//-------------------------------------------
//ITEM MENU (only available for active notes)
//-------------------------------------------
private fun addGiftToChildToChild() {
    val child: Child? = askUserToChooseChild()
    if (note != null) {
        if (note.addItem(Item(itemContents = readNextLine("\t Item Contents: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

fun updateItemContentsInNote() {
    val note: Note? = askUserToChooseActiveNote()
    if (note != null) {
        val item: Item? = askUserToChooseItem(note)
        if (item != null) {
            val newContents = readNextLine("Enter new contents: ")
            if (note.update(item.itemId, Item(itemContents = newContents))) {
                println("Item contents updated")
            } else {
                println("Item contents NOT updated")
            }
        } else {
            println("Invalid Item Id")
        }
    }
}

fun deleteAnItem() {
    val note: Note? = askUserToChooseActiveNote()
    if (note != null) {
        val item: Item? = askUserToChooseItem(note)
        if (item != null) {
            val isDeleted = note.delete(item.itemId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}



    //TODO fix
    fun searchChildren() {
        val searchTitle = readNextLine("Enter the description to search by: ")
        val searchResults = childAPI.searchChildrenByName(searchName)
        if (searchResults.isEmpty()) {
            println("No notes found")
        } else {
            println(searchResults)
        }
    }

    //TODO fix
    fun save() {
        try {
            childAPI.store()
        } catch (e: Exception) {
            System.err.println("Error writing to file: $e")
        }
    }

    fun load() {
        try {
            childAPI.load()
        } catch (e: Exception) {
            System.err.println("Error reading from file: $e")
        }
    }

fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

//------------------------------------
//HELPER FUNCTIONS
//------------------------------------

private fun askUserToChooseActiveNote(): Note? {
    listActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        val note = noteAPI.findNote(readNextInt("\nEnter the id of the note: "))
        if (note != null) {
            if (note.isNoteArchived) {
                println("Note is NOT Active, it is Archived")
            } else {
                return note //chosen note is active
            }
        } else {
            println("Note id is not valid")
        }
    }
    return null //selected note is not active
}

private fun askUserToChooseItem(note: Note): Item? {
    if (note.numberOfItems() > 0) {
        print(note.listItems())
        return note.findOne(readNextInt("\nEnter the id of the item: "))
    }
    else{
        println ("No items for chosen note")
        return null
    }
}










}