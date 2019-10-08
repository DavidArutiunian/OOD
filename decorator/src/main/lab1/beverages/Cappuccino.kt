package main.lab1.beverages

enum class CappuccinoSize {
    STANDARD,
    DOUBLE
}

class Cappuccino(private val mType: CappuccinoSize, description: String = when (mType) {
    CappuccinoSize.STANDARD -> "Cappuccino"
    CappuccinoSize.DOUBLE -> "Double Cappuccino"
}) : BeverageImpl(description) {
    override fun getCost() = when (mType) {
        CappuccinoSize.STANDARD -> 80.0
        CappuccinoSize.DOUBLE -> 120.0
    }
}
