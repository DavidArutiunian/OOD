package state

import gumballmachine.GumballMachineInternal

class SoldState(private val gumballMachine: GumballMachineInternal) : State {
    override fun insertQuarter() {
        println("Please wait, we're already giving you a gumball")
    }

    override fun ejectQuarter() {
        println("Sorry you already turned the crank")
    }

    override fun turnCrank() {
        println("Turning twice doesn't get you another gumball")
    }

    override fun dispense() {
        gumballMachine.releaseBall()
        if (gumballMachine.getBallCount() == 0) {
            println("Oops, out of gumballs")
            gumballMachine.setSoldOutState()
        } else {
            gumballMachine.setNoQuarterState()
        }
    }

    override fun toString(): String {
        return "delivering a gumball"
    }
}
