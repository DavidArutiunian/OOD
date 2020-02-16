package multigumballmachine.withstate

import gumballmachine.GumballMachine
import gumballmachine.MultiGumballMachineInternal
import state.MaxQuartersState
import state.MultiHasQuarterState
import state.MultiNoQuarterState
import state.MultiSoldOutState
import state.MultiSoldState
import state.RefillableState

class MultiGumballMachine(private var numBalls: Int) : GumballMachine, MultiGumballMachineInternal {
    companion object {
        const val MAX_QUARTERS = 5
    }

    private val soldOutState = MultiSoldOutState(this)
    private val noQuarterState = MultiNoQuarterState(this)
    private val hasQuarterState = MultiHasQuarterState(MAX_QUARTERS, this)
    private val soldState = MultiSoldState(this)
    private val maxQuartersState = MaxQuartersState(this)
    private var state: RefillableState = noQuarterState

    private var quartersCount = 0

    override fun getQuartersCount(): Int = quartersCount

    override fun addQuarter() {
        quartersCount++
        println("You inserted $quartersCount quarter${if (quartersCount > 1) "s" else ""}")
    }

    override fun returnAllQuarters() {
        quartersCount = 0
        println("All quarters returned")
    }

    override fun refill(numBalls: Int) {
        state.refill(numBalls)
    }

    override fun setMaxQuartersState() {
        state = maxQuartersState
    }

    override fun ejectQuarter() {
        state.ejectQuarter()
    }

    override fun insertQuarter() {
        state.insertQuarter()
    }

    override fun turnCrank() {
        state.turnCrank()
        state.dispense()
    }

    override fun releaseBall() {
        numBalls--
        quartersCount--
        println("A gumball comes rolling out the slot...")
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

    override fun toString(): String {
        return """
            Mighty Gumball, Inc.
            Kotlin-enabled Standing Gumball Model #2020 (with state)
            Inventory: $numBalls gumball${if (numBalls != 1) "s" else ""}
            Quarters count: $quartersCount
            Machine is $state
        """.trimIndent()
    }
}
