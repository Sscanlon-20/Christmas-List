import controllers.ChildAPI
import models.Child
import models.Gift
import models.Gift
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import kotlin.system.exitProcess

private val childAPI = ChildAPI()

fun main() = runMenu(

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

                0 -> exitApp()
                else -> println("Invalid menu choice: $option")
            }
        } while (true)
    }

    fun mainMenu() = readNextInt(
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
         > |   0) Exit                                             |
         > --------------------------------------------------------- 
         > ==>> """.trimMargin(">")
    )

    //------------------------------------
    //NOTE MENU
    //------------------------------------

    fun addChild() {
        val childName = readNextLine("Enter the child's name: ")
        val childGender = readNextChar("Enter gender (b or g): ")
        val childAge = readNextInt("Enter a child's ages: ")
        val behaviour = //how to do this????
        val totalAmount = readNextInt("How much should the child's gifts amount to? ")

        val isAdded = childAPI.add(Child(childName = childName, childGender = childGender, childAge = childAge, behaviour = behaviour, totalAmount = totalAmount))

        if (isAdded) {
            println("Added Successfully")
        } else {
            println("Add Failed")
        }
    }

