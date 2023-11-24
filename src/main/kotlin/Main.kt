import controllers.ChildAPI
import models.Child
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ValidateInput

private val childAPI = ChildAPI()

fun main() = runMenu()
fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addChild()
            2 -> listChildren()
            3 -> updateChild()
            4 -> deleteChild()

            5 -> addGift()
            6 -> addGiftToChild()
            7 -> updateGift()
            8 -> deleteGift()

            9 -> searchChildren()

            10 -> searchGifts()
            11 -> generateAgift()
            12 -> addOrRemoveGift()
            13 -> generateASuprise()

            14 -> save()
            15 -> load()
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
             > |   5) Add gift                                         |
             > |   6) Add gift to a child                              |
             > |   7) Update a gift                                    |
             > |   8) Delete a gift                                    |
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
                  > ---------------------------------
                  > |   1) List all children        |
                  > |   2) List all boys            |
                  > |   3) List all children over 3 |
                  > ---------------------------------
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
fun listByGender() = println(childAPI.listByGender())
fun listChildrenOver3() = println(childAPI.listChildrenOver3()) //TODO how??


fun updateChild() {
    // logger.info { "updateNotes() function invoked" }
    listChildren()
    if (childAPI.numberOfChildren() > 0) {
        // only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the child to update: ")
        if (childAPI.isValidIndex(indexToUpdate)) {
            val childName = readNextLine("Enter the child's name: ")
            val childGender = readNextChar("Enter gender (b or g): ")
            val childAge = readNextInt("Enter a child's ages: ")
            val behaviour = ValidateInput.readYN("Was the child good or bad (y/n)? ")
            val totalAmount = readNextInt("How much should the child's gifts amount to? ")

            if (childAPI.updateChild(indexToUpdate, Child(childName, childGender, childAge, behaviour, totalAmount))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no children with this index number")
        }
    }

    fun deleteChild() {

        listChildren()
        if (childAPI.numberOfChildren() > 0) {
            // only ask the user to choose the note to delete if notes exist
            val indexToDelete = readNextInt("Enter the index of the child to delete: ")
            // pass the index of the note to NoteAPI for deleting and check for success.
            val childToDelete = childAPI.deleteChild(indexToDelete)
            if (childToDelete != null) {
                println("Delete Successful! Deleted note: ${childToDelete.childName}")
            } else {
                println("Delete NOT Successful")
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
        logger.info { "exitApp() function invoked" }
        System.exit(0)
    }











}