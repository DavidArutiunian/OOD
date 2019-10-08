package main.lab2

fun String.toIntArray(): IntArray {
    return this.map { it.toInt() }.toIntArray()
}
