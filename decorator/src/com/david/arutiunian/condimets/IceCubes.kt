package com.david.arutiunian.condimets

import com.david.arutiunian.beverages.Beverage

enum class IceCubeType {
    DRY,
    WATER
}

class IceCubes(
    beverage: Beverage,
    private val mQuantity: Int,
    private val mType: IceCubeType)
    : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = (if (mType == IceCubeType.DRY) 10.0 else 5.0) * mQuantity

    override fun getCondimentDescription() = (if (mType == IceCubeType.DRY) "Dry" else "Water") + " ice cubes x $mQuantity"
}
