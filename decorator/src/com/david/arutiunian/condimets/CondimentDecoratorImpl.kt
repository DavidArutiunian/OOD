package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

abstract class CondimentDecoratorImpl protected constructor(beverage: Beverage) : Beverage {
    private val mBeverage = beverage

    override fun getDescription() = mBeverage.getDescription() + ", " + getCondimentDescription()

    override fun getCost() = mBeverage.getCost() + getCondimentCost()

    protected abstract fun getCondimentCost(): Double

    protected abstract fun getCondimentDescription(): String
}
