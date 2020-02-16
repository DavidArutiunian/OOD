package state

import gumballmachine.MultiGumballMachineInternal

class MultiSoldState(
    private val gumballMachine: MultiGumballMachineInternal
) : SoldState(gumballMachine), RefillableState {
    override fun refill(numBalls: Int) {
        println("Cannot refill while giving a gumball")
    }

    override fun dispense() {
        gumballMachine.releaseBall()
        if (gumballMachine.getBallCount() > 0) {
            gumballMachine.setHasQuarterState()
        } else {
            println("Oops, out of gumballs")
            gumballMachine.setSoldOutState()
        }
    }
}
