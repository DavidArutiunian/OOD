package com.david.arutiunian.beverages

class Tea(mDescription: String = "Tea") : BeverageImpl(mDescription) {
    override fun getCost() = 30.0
}
