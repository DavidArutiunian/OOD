package com.david.arutiunian.beverages

enum class MilkshakeSize {
    SMALL,
    MEDIUM,
    LARGE
}

class Milkshake(private val mMilkshakeSize: MilkshakeSize, mDescription: String = "Milkshake")
    : BeverageImpl(when (mMilkshakeSize) {
    MilkshakeSize.SMALL -> "Small $mDescription"
    MilkshakeSize.MEDIUM -> "Medium $mDescription"
    MilkshakeSize.LARGE -> "Large $mDescription"
}) {
    override fun getCost() = when (mMilkshakeSize) {
        MilkshakeSize.SMALL -> 50.0
        MilkshakeSize.MEDIUM -> 60.0
        MilkshakeSize.LARGE -> 80.0
    }
}
