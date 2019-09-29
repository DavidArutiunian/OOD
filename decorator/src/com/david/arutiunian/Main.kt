package com.david.arutiunian

import com.david.arutiunian.beverages.Beverage
import com.david.arutiunian.beverages.Coffee
import com.david.arutiunian.beverages.Tea
import com.david.arutiunian.condimets.Cinnamon
import com.david.arutiunian.condimets.Lemon
import java.util.*

fun main() {
    val input = Scanner(System.`in`)
    dialogWithUser(input)
}

fun dialogWithUser(input: Scanner) {
    println("Type 1 for coffee or 2 for tea")

    var beverage: Beverage = when (input.nextInt()) {
        1 -> Coffee()
        2 -> Tea()
        else -> return
    }

    loop@ while (true) {
        println("1 - Lemon, 2 - Cinnamon, 0 - Checkout")
        beverage = when (input.nextInt()) {
            1 -> Lemon(beverage, 2)
            2 -> Cinnamon(beverage)
            0 -> break@loop
            else -> return
        }
    }

    println(beverage.getDescription() + ", cost: " + beverage.getCost())
}
