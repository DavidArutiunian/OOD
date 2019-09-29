package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

enum class LiquorType {
    HAZEL,
    CHOCOLATE
}

class Liquor(beverage: Beverage, private val mType: LiquorType) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 50.0

    override fun getCondimentDescription() = (if (mType == LiquorType.HAZEL) "Hazel" else "Chocolate") + " liquor"
}
