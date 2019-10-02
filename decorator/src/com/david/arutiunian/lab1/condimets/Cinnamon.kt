package com.david.arutiunian.lab1.condimets

import com.david.arutiunian.lab1.beverages.Beverage

class Cinnamon(beverage: Beverage) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 20.0

    override fun getCondimentDescription() = "Cinnamon"
}
