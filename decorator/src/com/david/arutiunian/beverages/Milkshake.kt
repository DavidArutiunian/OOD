package com.david.arutiunian.beverages

class Milkshake(mDescription: String = "Milkshake") : BeverageImpl(mDescription) {
    override fun getCost() = 80.0
}
