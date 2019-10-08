package main.lab1.condimets

import main.lab1.beverages.Beverage

enum class LiquorType {
    HAZEL,
    CHOCOLATE
}

class Liquor(beverage: Beverage, private val mType: LiquorType) : CondimentDecoratorImpl(beverage) {
    override fun getCondimentCost() = 50.0

    override fun getCondimentDescription() = when (mType) {
        LiquorType.HAZEL -> "Hazel"
        LiquorType.CHOCOLATE -> "Chocolate"
    } + " liquor"
}
