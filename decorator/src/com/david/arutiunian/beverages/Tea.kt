package com.david.arutiunian.beverages

enum class TeaType {
    GREEN,
    WHITE,
    BLACK,
    OOLONG,
}

class Tea(type: TeaType, mDescription: String = "Tea")
    : BeverageImpl(when (type) {
    TeaType.GREEN -> "Green $mDescription"
    TeaType.WHITE -> "White $mDescription"
    TeaType.BLACK -> "Black $mDescription"
    TeaType.OOLONG -> "Oolong $mDescription"
}) {
    override fun getCost() = 30.0
}
