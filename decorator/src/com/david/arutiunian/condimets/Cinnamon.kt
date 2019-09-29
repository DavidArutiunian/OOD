package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

class Cinnamon(beverage: Beverage) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 20.0

    override fun getCondimentDescription() = "Cinnamon"
}
