package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

enum class SyrupType {
    CHOCOLATE,
    MAPLE
}

class Syrup(beverage: Beverage, private val mType: SyrupType) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 15.0

    override fun getCondimentDescription() = (if (mType == SyrupType.CHOCOLATE) "Chocolate" else "Maple") + " syrup"
}
