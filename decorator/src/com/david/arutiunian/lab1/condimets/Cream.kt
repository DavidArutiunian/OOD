package com.david.arutiunian.lab1.condimets

import com.david.arutiunian.lab1.beverages.Beverage

class Cream(beverage: Beverage) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 25.0

    override fun getCondimentDescription() = "Cream"
}
