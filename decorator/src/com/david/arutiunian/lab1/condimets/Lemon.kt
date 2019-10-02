package com.david.arutiunian.lab1.condimets

import com.david.arutiunian.lab1.beverages.Beverage

class Lemon(beverage: Beverage, private val mQuantity: Int = 1) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 10.0 * mQuantity

    override fun getCondimentDescription() = "Lemon x $mQuantity"
}
