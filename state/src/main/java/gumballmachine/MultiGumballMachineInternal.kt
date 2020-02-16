package gumballmachine

interface MultiGumballMachineInternal : GumballMachineInternal {
    fun getQuartersCount(): Int

    fun addQuarter()

    fun returnAllQuarters()

    fun refill(numBalls: Int)

    fun setMaxQuartersState()
}
