package state

import gumballmachine.MultiGumballMachineInternal

class MultiSoldOutState(
    private val gumballMachine: MultiGumballMachineInternal
) : SoldOutState(gumballMachine), RefillableState {
    override fun refill(numBalls: Int) {
        gumballMachine.refill(numBalls)
        if (gumballMachine.getQuartersCount() > 0) {
            gumballMachine.setHasQuarterState()
        } else {
            gumballMachine.setNoQuarterState()
        }
    }

    override fun ejectQuarter() {
        if (gumballMachine.getQuartersCount() > 0) {
            gumballMachine.returnAllQuarters()
        } else {
            super.ejectQuarter()
        }
    }
}
