package utils

import models.Child
import models.Gift

object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(notesToFormat: List<Child>): String =
        notesToFormat
            .joinToString(separator = "\n") { child -> "$child" }

    @JvmStatic
    fun formatSetString(itemsToFormat: Set<Gift>): String =
        itemsToFormat
            .joinToString(separator = "\n") { gift -> "\t$gift" }
}
