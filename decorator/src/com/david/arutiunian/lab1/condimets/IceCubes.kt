package com.david.arutiunian.lab1.condimets

import com.david.arutiunian.lab1.beverages.Beverage

enum class IceCubeType {
    DRY,
    WATER
}

class IceCubes(
    beverage: Beverage,
    private val mQuantity: Int,
    private val mType: IceCubeType)
    : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = when (mType) {
        IceCubeType.DRY -> 10.0
        IceCubeType.WATER -> 5.0
    } * mQuantity

    override fun getCondimentDescription() = when (mType) {
        IceCubeType.DRY -> "Dry"
        IceCubeType.WATER -> "Water"
    } + " ice cubes x $mQuantity"
}
