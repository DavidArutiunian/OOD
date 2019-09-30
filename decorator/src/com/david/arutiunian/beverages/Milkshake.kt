package com.david.arutiunian.beverages

enum class MilkshakeSize {
    SMALL,
    MEDIUM,
    LARGE
}

class Milkshake(private val mMilkshakeSize: MilkshakeSize, description: String = "Milkshake")
    : BeverageImpl(when (mMilkshakeSize) {
    MilkshakeSize.SMALL -> "Small $description"
    MilkshakeSize.MEDIUM -> "Medium $description"
    MilkshakeSize.LARGE -> "Large $description"
}) {
    override fun getCost() = when (mMilkshakeSize) {
        MilkshakeSize.SMALL -> 50.0
        MilkshakeSize.MEDIUM -> 60.0
        MilkshakeSize.LARGE -> 80.0
    }
}
