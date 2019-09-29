package com.david.arutiunian.beverages

class DoubleLatte(mDescription: String = "Double Latte") : BeverageImpl(mDescription) {
    override fun getCost() = 130.0
}
