package com.david.arutiunian.beverages

class DoubleCappuccino(mDescription: String = "Double Cappuccino") : BeverageImpl(mDescription) {
    override fun getCost() = 120.0
}
