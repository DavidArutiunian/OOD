package com.david.arutiunian.beverages

class Cappuccino(mDescription: String = "Cappuccino") : BeverageImpl(mDescription) {
    override fun getCost() = 80.0
}
