package main.lab1.condimets

import main.lab1.beverages.Beverage

class Cinnamon(beverage: Beverage) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 20.0

    override fun getCondimentDescription() = "Cinnamon"
}
