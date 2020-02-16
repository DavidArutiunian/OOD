package state

import gumballmachine.MultiGumballMachineInternal

class MultiNoQuarterState(
    private val gumballMachine: MultiGumballMachineInternal
) : NoQuarterState(gumballMachine), RefillableState {
    override fun refill(numBalls: Int) {
        gumballMachine.refill(numBalls)
    }

    override fun insertQuarter() {
        gumballMachine.addQuarter()
        gumballMachine.setHasQuarterState()
    }
}
