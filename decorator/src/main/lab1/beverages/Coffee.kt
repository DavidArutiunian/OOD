package main.lab1.beverages

class Coffee(mDescription: String = "Coffee") : BeverageImpl(mDescription) {
    override fun getCost() = 60.0
}
