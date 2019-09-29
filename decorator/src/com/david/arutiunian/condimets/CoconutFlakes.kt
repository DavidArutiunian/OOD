package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

class CoconutFlakes(beverage: Beverage, private val mMass: Double) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 1.0 + mMass

    override fun getCondimentDescription() = "Coconut flakes ${mMass}g"
}
