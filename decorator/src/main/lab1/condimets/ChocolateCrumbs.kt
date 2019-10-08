package main.lab1.condimets

import main.lab1.beverages.Beverage

class ChocolateCrumbs(beverage: Beverage, private val mMass: Double) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 2.0 * mMass

    override fun getCondimentDescription() = "Chocolate crumbs ${mMass}g"
}
