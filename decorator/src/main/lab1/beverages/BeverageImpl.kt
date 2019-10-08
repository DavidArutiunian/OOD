package main.lab1.beverages

abstract class BeverageImpl(private val mDescription: String) : Beverage {
    override fun getDescription() = mDescription
}
