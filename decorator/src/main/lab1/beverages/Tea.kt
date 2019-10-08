package main.lab1.beverages

enum class TeaType {
    GREEN,
    WHITE,
    BLACK,
    OOLONG,
}

class Tea(type: TeaType, description: String = "Tea")
    : BeverageImpl(when (type) {
    TeaType.GREEN -> "Green $description"
    TeaType.WHITE -> "White $description"
    TeaType.BLACK -> "Black $description"
    TeaType.OOLONG -> "Oolong $description"
}) {
    override fun getCost() = 30.0
}
