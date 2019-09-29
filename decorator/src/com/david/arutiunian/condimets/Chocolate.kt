package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

class Chocolate(beverage: Beverage, private var mQuantity: Int) : CondimentDecoratorImpl(beverage) {
    init {
        mQuantity = minOf(mQuantity, 5)
    }

    override fun getCondimentCost() = 10.0 * mQuantity

    override fun getCondimentDescription() = "Chocolate x $mQuantity"


}
