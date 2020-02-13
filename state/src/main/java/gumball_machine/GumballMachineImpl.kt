package gumball_machine

import state.*

class GumballMachineImpl(private var numBalls: Int) : GumballMachine {
    private val soldState = SoldState(this)
    private val soldOutState = SoldOutState(this)
    private val noQuarterState = NoQuarterState(this)
    private val hasQuarterState = HasQuarterState(this)
    private var state: State? = noQuarterState

    override fun ejectQuarter() {
        state?.ejectQuarter()
    }

    override fun insertQuarter() {
        state?.insertQuarter()
    }

    override fun turnCrank() {
        state?.turnCrank()
        state?.dispense()
    }

    override fun toString(): String {
        return """
            Mighty Gumball, Inc.
            Kotlin-enabled Standing Gumball Model #2020 (with state)
            Inventory: $numBalls gumball${if (numBalls != 1) "s" else ""}
            Machine is ${state.toString()}
        """.trimIndent()
    }

    override fun releaseBall() {
        if (numBalls != 0) {
            println("A gumball comes rolling out the slot...")
            --numBalls
        }
    }

    override fun getBallCount(): Int = numBalls

    override fun setSoldOutState() {
        state = soldOutState
    }

    override fun setNoQuarterState() {
        state = noQuarterState
    }

    override fun setSoldState() {
        state = soldState
    }

    override fun setHasQuarterState() {
        state = hasQuarterState
    }
}