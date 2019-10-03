package com.david.arutiunian.lab2

fun String.toIntArray(): IntArray {
    return this.map { it.toInt() }.toIntArray()
}
