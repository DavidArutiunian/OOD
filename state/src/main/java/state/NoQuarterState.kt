package state

import gumball_machine.GumballMachine

class NoQuarterState(private val gumballMachine: GumballMachine) : State {
    override fun insertQuarter() {
        println("You inserted a quarter")
        gumballMachine.setHasQuarterState()
    }

    override fun ejectQuarter() {
        println("You haven't inserted a quarter")
    }

    override fun turnCrank() {
        println("You turned but there's no quarter")
    }

    override fun dispense() {
        println("You need to pay first")
    }

    override fun toString(): String {
        return "waiting for quarter"
    }
}