package main.lab1.condimets

import main.lab1.beverages.Beverage

abstract class CondimentDecoratorImpl protected constructor(beverage: Beverage) : Beverage {
    private val mBeverage = beverage

    final override fun getDescription() = mBeverage.getDescription() + ", " + getCondimentDescription()

    final override fun getCost() = mBeverage.getCost() + getCondimentCost()

    protected abstract fun getCondimentCost(): Double

    protected abstract fun getCondimentDescription(): String
}
