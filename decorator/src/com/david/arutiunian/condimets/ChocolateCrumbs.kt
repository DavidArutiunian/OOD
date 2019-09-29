package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

class ChocolateCrumbs(beverage: Beverage, private val mMass: Double) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 2.0 * mMass

    override fun getCondimentDescription() = "Chocolate crumbs ${mMass}g"
}
