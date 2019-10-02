package com.david.arutiunian.lab1.beverages

enum class LatteSize {
    STANDARD,
    DOUBLE
}

class Latte(private val mType: LatteSize, description: String = when (mType) {
    LatteSize.STANDARD -> "Latte"
    LatteSize.DOUBLE -> "Double Latte"
}) : BeverageImpl(description) {
    override fun getCost() = when (mType) {
        LatteSize.STANDARD -> 90.0
        LatteSize.DOUBLE -> 130.0
    }
}
