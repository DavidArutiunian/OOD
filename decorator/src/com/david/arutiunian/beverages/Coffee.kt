package com.david.arutiunian.beverages

class Coffee(mDescription: String = "Coffee") : BeverageImpl(mDescription) {
    override fun getCost() = 60.0
}
