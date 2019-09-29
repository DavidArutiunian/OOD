package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

class Cream(beverage: Beverage) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 25.0

    override fun getCondimentDescription() = "Cream"
}
