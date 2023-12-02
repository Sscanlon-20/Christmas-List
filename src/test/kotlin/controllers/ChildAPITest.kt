package controllers

import models.Child
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals

class ChildAPITest {
        private var childOne: Child? = null
        private var childTwo: Child? = null
        private var childThree: Child? = null
        private var childFour: Child? = null
        private var populatedList: ChildAPI? = ChildAPI(XMLSerializer(File("notes.xml")))
        private var emptyList: ChildAPI? = ChildAPI(XMLSerializer(File("notes.xml")))

        @BeforeEach
        fun setup() {
            childOne = Child(0, "Amie", 'g', 1)
            childTwo = Child(1, "Dylan", 'b', 15)
            childThree = Child(2, "Ruby", 'g', 2)
            childFour= Child(3, "Brian", 'b', 10)

            populatedList!!.add(childOne!!)
            populatedList!!.add(childTwo!!)
            populatedList!!.add(childThree!!)
            populatedList!!.add(childFour!!)
        }

        @AfterEach
        fun tearDown() {
            childOne= null
            childTwo = null
            childThree = null
            childFour = null
            populatedList = null
            emptyList = null
        }

    @Nested
    inner class AddChild {
        @Test
        fun `adding a Child to a populated list adds to ArrayList`() {
            val newChild = Child(4, "abbey", 'g', 5)
            assertEquals(4, populatedList!!.numberOfChildren())
            assertTrue(populatedList!!.add(newChild))
            assertEquals(5, populatedList!!.numberOfChildren())
            assertEquals(newChild, populatedList!!.findChild(populatedList!!.numberOfChildren() - 1))
        }

        @Test
        fun `adding a Child to an empty list adds to ArrayList`() {
            val newChild = Child(4, "abbey", 'g', 5)
            assertEquals(0, emptyList!!.numberOfChildren())
            assertTrue(emptyList!!.add(newChild))
            assertEquals(1, emptyList!!.numberOfChildren())
            assertEquals(newChild, emptyList!!.findChild(0))
        }
    }

    /*@Test @Nested //TODO HELP
     inner class DeleteChildren {


         fun `deleting a Child that does not exist, returns null`() {
             assertNull(emptyList!!.delete(0))
             assertNull(populatedList!!.delete(-1))
             assertNull(populatedList!!.delete(5))
         }

         @Test
         fun `deleting a child that exists delete and returns deleted object`() {
             assertEquals(5, populatedList!!.numberOfChildren())
             assertEquals(childOne, populatedList!!.delete(4))
             assertEquals(4, populatedList!!.numberOfChildren())
             assertEquals(childTwo, populatedList!!.delete(0))
             assertEquals(3, populatedList!!.numberOfChildren())
         }
     }*/

    @Nested
    inner class UpdateChildren {
        @Test
        fun `updating a child that does not exist returns false`() {
            assertFalse(populatedList!!.updateChild(6, Child(6, "James", 'b', 9)))
            assertFalse(populatedList!!.updateChild(-1, Child(6, "James", 'b', 9)))
            assertFalse(emptyList!!.updateChild(0, Child(6, "James", 'b', 9)))
        }

        @Test
        fun `updating a child that exists returns true and updates`() {

            assertEquals(childTwo, populatedList!!.findChild(1))
            assertEquals("Dylan", populatedList!!.findChild(1)!!.childName)
            assertEquals('b', populatedList!!.findChild(1)!!.childGender)
            assertEquals(15, populatedList!!.findChild(1)!!.childAge)

            // update note 5 with new information and ensure contents updated successfully
            assertTrue(populatedList!!.updateChild(1, Child(3, "Ruby", 'g', 2)))
            assertEquals("Ruby", populatedList!!.findChild(1)!!.childName)
            assertEquals('g', populatedList!!.findChild(1)!!.childGender)
            assertEquals(2, populatedList!!.findChild(1)!!.childAge)
        }
    }
    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty notes.XML file.
            val storingChildren = ChildAPI(XMLSerializer(File("children.xml")))
            storingChildren.store()

            // Loading the empty notes.xml file into a new object
            val loadedChildren = ChildAPI(XMLSerializer(File("children.xml")))
            loadedChildren.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingChildren.numberOfChildren())
            assertEquals(0, loadedChildren.numberOfChildren())
            assertEquals(storingChildren.numberOfChildren(), loadedChildren.numberOfChildren())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            // Storing 3 notes to the notes.XML file.
            val storingChilden = ChildAPI(XMLSerializer(File("children.xml")))
            storingChilden.add(childOne!!)
            storingChilden.add(childTwo!!)
            storingChilden.add(childThree!!)
            storingChilden.store()

            // Loading notes.xml into a different collection
            val loadedChildren = ChildAPI(XMLSerializer(File("children.xml")))
            loadedChildren.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(3, storingChilden.numberOfChildren())
            assertEquals(3, loadedChildren.numberOfChildren())
            assertEquals(storingChilden.numberOfChildren(), loadedChildren.numberOfChildren())
            assertEquals(storingChilden.findChild(0), loadedChildren.findChild(0))
            assertEquals(storingChilden.findChild(1), loadedChildren.findChild(1))
            assertEquals(storingChilden.findChild(2), loadedChildren.findChild(2))
        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty notes.json file.
            val storingChildren = ChildAPI(JSONSerializer(File("children.json")))
            storingChildren.store()

            // Loading the empty notes.json file into a new object
            val loadedChildren = ChildAPI(JSONSerializer(File("children.json")))
            loadedChildren.load()

            // Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
            assertEquals(0, storingChildren.numberOfChildren())
            assertEquals(0, loadedChildren.numberOfChildren())
            assertEquals(storingChildren.numberOfChildren(), loadedChildren.numberOfChildren())
        }

        @Test
        fun `saving and loading an loaded collection in JSON doesn't loose data`() {
            // Storing 3 notes to the notes.json file.
            val storingChildren = ChildAPI(JSONSerializer(File("children.json")))
            storingChildren.add(childOne!!)
            storingChildren.add(childTwo!!)
            storingChildren.add(childThree!!)
            storingChildren.store()

            // Loading notes.json into a different collection
            val loadedChildren = ChildAPI(JSONSerializer(File("children.json")))
            loadedChildren.load()

            // Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
            assertEquals(3, storingChildren.numberOfChildren())
            assertEquals(3, loadedChildren.numberOfChildren())
            assertEquals(storingChildren.numberOfChildren(), loadedChildren.numberOfChildren())
            assertEquals(storingChildren.findChild(0), loadedChildren.findChild(0))
            assertEquals(storingChildren.findChild(1), loadedChildren.findChild(1))
            assertEquals(storingChildren.findChild(2), loadedChildren.findChild(2))
        }
    }
}


//todo listchildren

//todo addGiftToChild
//todo updateGiftDetailsForChild
//todo deleteGiftFromChild

//todo searchChildByName
//todo searchGiftByType
//todo totalCostOfChildsList



