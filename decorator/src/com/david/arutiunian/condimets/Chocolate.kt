package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

class Chocolate(beverage: Beverage, private var mQuantity: Int) : CondimentDecoratorImpl(beverage) {
    companion object {
        const val MAX_QUANTITY = 5
    }

    init {
        mQuantity = minOf(mQuantity, MAX_QUANTITY)
    }

    override fun getCondimentCost() = 10.0 * mQuantity

    override fun getCondimentDescription() = "Chocolate x $mQuantity"


}
