package models

data class Gift (var giftId: Int = 0,
                 var giftName: String,
                 var cost : Int = 0,
                 var whereToBuy: String,
                 var category: String)

{
    override fun toString(): String {
            return "${giftId}Id: $giftName ($cost) $whereToBuy [$category]"
    }

}