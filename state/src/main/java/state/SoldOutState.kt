package state

import gumballmachine.GumballMachineInternal

open class SoldOutState(private val gumballMachine: GumballMachineInternal) : State {
    override fun insertQuarter() {
        println("You can't insert a quarter, the machine is sold out")
    }

    override fun ejectQuarter() {
        println("You can't eject, you haven't inserted a quarter yet")
    }

    override fun turnCrank() {
        println("You turned but there're no gumballs")
    }

    override fun dispense() {
        println("No gumball dispensed")
    }

    override fun toString(): String {
        return "sold out"
    }
}
