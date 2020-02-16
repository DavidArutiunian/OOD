package state

import gumballmachine.MultiGumballMachineInternal

class MaxQuartersState(
    private val gumballMachine: MultiGumballMachineInternal
) : RefillableState {
    override fun refill(numBalls: Int) {
        gumballMachine.refill(numBalls)
    }

    override fun insertQuarter() {
        println("Max number of quarters")
    }

    override fun ejectQuarter() {
        gumballMachine.returnAllQuarters()
        gumballMachine.setNoQuarterState()
    }

    override fun turnCrank() {
        println("You turned...")
        gumballMachine.setSoldState()
    }

    override fun dispense() {
        println("No gumball dispensed")
    }

    override fun toString(): String {
        return "max quarters"
    }
}
