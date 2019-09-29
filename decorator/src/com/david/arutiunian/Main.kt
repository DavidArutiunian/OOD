package com.david.arutiunian

import com.david.arutiunian.beverages.*
import com.david.arutiunian.condimets.Cinnamon
import com.david.arutiunian.condimets.Lemon
import java.util.*

fun main() {
    val input = Scanner(System.`in`)
    dialogWithUser(input)
}

fun dialogWithUser(input: Scanner) {
    println("1 - Coffee, 2 - Tea, 3 - Milkshake")

    var beverage: Beverage? = when (input.nextInt()) {
        1 -> Coffee()
        2 -> chooseTeaType(input)
        3 -> chooseMilkshakeSize(input)
        else -> return
    } ?: return

    loop@ while (true) {
        println("1 - Lemon, 2 - Cinnamon, 0 - Checkout")
        beverage = when (input.nextInt()) {
            1 -> Lemon(beverage!!, 2)
            2 -> Cinnamon(beverage!!)
            0 -> break@loop
            else -> return
        }
    }

    println(beverage?.getDescription() + ", cost: " + beverage?.getCost())
}

fun chooseMilkshakeSize(input: Scanner): Beverage? {
    println("1 - Small, 2 - Medium, 3 - Large")
    val size = when(input.nextInt()) {
        1 -> MilkshakeSize.SMALL
        2 -> MilkshakeSize.MEDIUM
        3 -> MilkshakeSize.LARGE
        else -> return null
    }
    return Milkshake(size)
}

fun chooseTeaType(input: Scanner): Beverage? {
    println("1 - Green, 2 - White, 3 - Black, 4 - Oolong")
    val type = when (input.nextInt()) {
        1 -> TeaType.GREEN
        2 -> TeaType.WHITE
        3 -> TeaType.BLACK
        4 -> TeaType.OOLONG
        else -> return null
    }
    return Tea(type)
}
