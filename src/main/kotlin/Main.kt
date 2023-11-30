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
            11 -> listAllGiftsForChild()
            12 -> generateASuprise()

            13 -> save()
            14 -> load()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun generateASuprise() {
    TODO("Not yet implemented")
}

fun listAllGiftsForChild() {
    TODO("Not yet implemented")
}

fun generateAgift() {
    TODO("Not yet implemented")
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
             > |   5) Add gift to a child                              |
             > |   6) Update gift details for a child                  |
             > |   7) Delete a gift from child                         |
             > ---------------------------------------------------------
             > | REPORT MENU FOR NOTES                                 | 
             > |   8) Search for all children (by name)                |
             > --------------------------------------------------------- 
             > | REPORT MENU FOR ITEMS 
             >     9) Search Gift                                     |                                
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

fun listByGender()
    { val childGender = readNextChar("Enter gender (b or g): ")
       println(childAPI.listByGender(childGender))
    }

fun listChildrenOver3() = println(childAPI.listChildrenOver3())

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
//CHILD REPORTS MENU
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
//GIFT REPORTS MENU
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
//GIFT MENU
//-------------------------------------------
private fun addGiftToChild() {
    val child: Child? = askUserToChooseChild()
    if (child != null) {
        if (child.addGift(Gift(
                giftName = readNextLine("\t Gift Name: "),
                whereToBuy = readNextLine("\t Where to buy it"),
                cost = readNextInt("\t How much is it: "),
                category = readNextLine("\t What category is it: "),
        )))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

fun updateGiftDetailsForChild() {
    val child: Child? = askUserToChooseChild()
    if (child != null) {
        val gift: Gift? = askUserToChooseGift(child)
        if (gift != null) {
            if (childAPI.updateGift(child.childId, gift.giftId,
                    Gift(giftName = readNextLine("\t Gift Name: "),
                        whereToBuy = readNextLine("\t Where to buy it"),
                        cost = readNextInt("\t How much is it: "),
                        category = readNextLine("\t What category is it: "),))) {
                println("Item contents updated")
            } else {
                println("Item contents NOT updated")
            }
        } else {
            println("Invalid Item Id")
        }
    }
}

fun deleteGiftFromChild() {
    val child: Child? = askUserToChooseChild()
    if (child != null) {
        val gift: Gift? = askUserToChooseGift(child)
        if (gift != null) {
            val isDeleted = child.delete(gift.giftId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

fun searchChildren() { //todo why greyed out how to fix
    val searchChild = readNextLine("Enter the description to search by: ")
    val searchResults = childAPI.searchChildrenByName(searchChild)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

//todo fix
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

private fun askUserToChooseChild(): Child? {
    listChildren()
    if (childAPI.numberOfChildren() > 0) {
        val child = childAPI.findChild(readNextInt("\nEnter the id of the child: "))
        return if (child == null) {
            println("No child listed")
            return null
        } else {
            return child
        }
    }
    return null
}

private fun askUserToChooseGift(child: Child): Gift? {
    if (child.numberOfGifts() > 0) {
        print(child.listGifts())
        return child.findOne(readNextInt("\nEnter the id of the gift: "))
    } else {
        println("No gifts for chosen child")
        return null
    }
}










