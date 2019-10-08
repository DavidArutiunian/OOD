package main.lab1

import main.lab1.beverages.*
import main.lab1.condimets.*
import java.util.*

fun main() {
    val input = Scanner(System.`in`)
    dialogWithUser(input)
}

fun dialogWithUser(input: Scanner) {
    val beverages = mapOf(
        Pair("Cappuccino", { Cappuccino(CappuccinoSize.STANDARD) }),
        Pair("Double Cappuccino", { Cappuccino(CappuccinoSize.DOUBLE) }),
        Pair("Coffee", { Coffee() }),
        Pair("Latte", { Latte(LatteSize.STANDARD) }),
        Pair("Double Latte", { Latte(LatteSize.DOUBLE) }),
        Pair("Milkshake", { milkshakeDialog(input) }),
        Pair("Tea", { teaDialog(input) })
    )
    beverages.keys.forEachIndexed { index, beverage -> println("${index + 1} - $beverage") }

    val inputBeverageIndex = input.nextInt()
    val inputBeverageKey = beverages.keys.elementAtOrNull(inputBeverageIndex - 1)
    var beverage = beverages[inputBeverageKey]?.invoke() ?: return

    while (true) {
        val condiments = mapOf(
            Pair("Lemon", { Lemon(beverage, 2) }),
            Pair("Cinnamon", { Cinnamon(beverage) }),
            Pair("Chocolate", { Chocolate(beverage, 1) }),
            Pair("Chocolate crumbs", { ChocolateCrumbs(beverage, 50.0) }),
            Pair("Coconut flakes", { CoconutFlakes(beverage, 50.0) }),
            Pair("Cream", { Cream(beverage) }),
            Pair("Ice cubes", { iceCubesDialog(input)?.invoke(beverage) }),
            Pair("Liquor", { liquorDialog(input)?.invoke(beverage) }),
            Pair("Syrup", { syrupDialog(input)?.invoke(beverage) })
        )
        condiments.keys.forEachIndexed { index, condiment -> println("${index + 1} - $condiment") }
        println("0 - Checkout")
        val inputCondimentIndex = input.nextInt()
        if (inputCondimentIndex == 0) break
        val inputCondimentKey = condiments.keys.elementAtOrNull(inputCondimentIndex - 1)
        beverage = condiments[inputCondimentKey]?.invoke() ?: continue
    }

    println(beverage.getDescription() + ", cost: " + beverage.getCost())
}

fun syrupDialog(input: Scanner): ((beverage: Beverage) -> Beverage)? {
    println("1 - Chocolate, 2 - Maple")
    val type = when (input.nextInt()) {
        1 -> SyrupType.CHOCOLATE
        2 -> SyrupType.MAPLE
        else -> return null
    }
    return { beverage -> Syrup(beverage, type) }
}

fun liquorDialog(input: Scanner): ((beverage: Beverage) -> Beverage)? {
    println("1 - Hazel, 2 - Chocolate")
    val type = when (input.nextInt()) {
        1 -> LiquorType.HAZEL
        2 -> LiquorType.CHOCOLATE
        else -> return null
    }
    return { beverage -> Liquor(beverage, type) }
}

fun iceCubesDialog(input: Scanner): ((beverage: Beverage) -> Beverage)? {
    println("1 - Dry, 2 - Water")
    val type = when (input.nextInt()) {
        1 -> IceCubeType.DRY
        2 -> IceCubeType.WATER
        else -> return null
    }
    return { beverage -> IceCubes(beverage, 2, type) }
}

fun milkshakeDialog(input: Scanner): Beverage? {
    println("1 - Small, 2 - Medium, 3 - Large")
    val size = when (input.nextInt()) {
        1 -> MilkshakeSize.SMALL
        2 -> MilkshakeSize.MEDIUM
        3 -> MilkshakeSize.LARGE
        else -> return null
    }
    return Milkshake(size)
}

fun teaDialog(input: Scanner): Beverage? {
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
