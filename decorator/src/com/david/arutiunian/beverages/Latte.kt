package com.david.arutiunian.beverages

class Latte(mDescription: String = "Latte") : BeverageImpl(mDescription) {
    override fun getCost() = 90.0
}
