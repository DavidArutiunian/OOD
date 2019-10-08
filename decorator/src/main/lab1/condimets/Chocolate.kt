package main.lab1.condimets

import main.lab1.beverages.Beverage

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
