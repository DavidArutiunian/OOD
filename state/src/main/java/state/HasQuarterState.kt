package state

import gumballmachine.GumballMachine

class HasQuarterState(private val gumballMachine: GumballMachine) : State {
    override fun insertQuarter() {
        println("You can't insert another quarter")
    }

    override fun ejectQuarter() {
        println("Quarter returned")
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
        return "waiting for the turn of crank"
    }
}
