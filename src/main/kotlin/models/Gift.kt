package models

data class Gift (var giftId: Int = 0,
                 var giftName: String,
                 var cost : Int = 0,
                 var whereToBuy: String,
                 var category: String,
                 var minAge: Int = 0,
                 var recommendedGender: Char = 'n')
{
    override fun toString(): String {
        //TODO Lift Return out in labs
            return "${giftId}Id: $giftName ($cost) $whereToBuy [$category] $minAge $recommendedGender"
    }

}