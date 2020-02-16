package state

import gumballmachine.MultiGumballMachineInternal

open class MultiHasQuarterState(
    private val maxQuarters: Int,
    private val gumballMachine: MultiGumballMachineInternal
) : HasQuarterState(gumballMachine), RefillableState {
    override fun refill(numBalls: Int) {
        gumballMachine.refill(numBalls)
    }

    override fun insertQuarter() {
        gumballMachine.addQuarter()
        if (gumballMachine.getQuartersCount() == maxQuarters) {
            gumballMachine.setHasQuarterState()
        }
    }

    override fun ejectQuarter() {
        gumballMachine.returnAllQuarters()
        gumballMachine.setNoQuarterState()
    }
}
