package com.david.arutiunian.beverages

abstract class BeverageImpl(private val mDescription: String) : Beverage {
    override fun getDescription() = mDescription
}
