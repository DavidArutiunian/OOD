package main.lab1.condimets

import main.lab1.beverages.Beverage

class Cream(beverage: Beverage) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 25.0

    override fun getCondimentDescription() = "Cream"
}
