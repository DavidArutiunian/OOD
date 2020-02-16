package multigumballmachine.naive

import gumballmachine.GumballMachine

class MultiGumballMachine(numBalls: Int) : GumballMachine {
    private enum class State {
        SOLD_OUT,
        NO_QUARTER,
        HAS_QUARTER,
        SOLD
    }

    companion object {
        const val MAX_QUARTERS = 5
    }

    private var state: State = if (numBalls > 0) State.NO_QUARTER else State.SOLD_OUT

    private var gumBallsCount = numBalls
    private var quartersCount = 0

    override fun ejectQuarter() {
        when (state) {
            State.HAS_QUARTER -> returnAllQuarters()

            State.NO_QUARTER -> println("You haven't inserted a quarter")

            State.SOLD -> println("Sorry, you already turned the crank")

            State.SOLD_OUT -> if (quartersCount > 0) {
                returnAllQuarters()
            } else {
                println("You can't eject, you haven't inserted a quarter yet")
            }
        }
    }

    override fun insertQuarter() {
        when (state) {
            State.HAS_QUARTER -> addQuarter()

            State.NO_QUARTER -> {
                addQuarter()
                state = State.HAS_QUARTER
            }

            State.SOLD_OUT -> println("You can't insert a quarter, the machine is sold out")

            State.SOLD -> println("Please wait, we're already giving you a gumball")
        }
    }

    override fun turnCrank() {
        when (state) {
            State.SOLD -> println("Turning twice doesn't get you another gumball!")

            State.NO_QUARTER -> println("You turned but there's no quarter")

            State.SOLD_OUT -> println("You turned but there're no gumballs")

            State.HAS_QUARTER -> {
                println("You turned...")
                state = State.SOLD
            }
        }
        dispense()
    }

    private fun dispense() {
        when (state) {
            State.SOLD -> {
                quartersCount--
                releaseBall()
            }

            State.NO_QUARTER -> println("You need to pay first")

            State.SOLD_OUT, State.HAS_QUARTER -> println("No gumball dispensed")
        }
    }

    private fun addQuarter() {
        if (quartersCount < MAX_QUARTERS) {
            quartersCount++
            println("You inserted $quartersCount quarter${if (quartersCount > 1) "s" else ""}")
        } else {
            println("Quarters limit exceeded")
        }
    }

    private fun returnAllQuarters() {
        quartersCount = 0
        state = State.NO_QUARTER
        println("All quarters returned")
    }

    private fun releaseBall() {
        println("A gumball comes rolling out the slot...")
        gumBallsCount--
        state = when {
            gumBallsCount == 0 -> {
                println("Oops, out of gumballs")
                State.SOLD_OUT
            }

            quartersCount > 0 -> {
                State.HAS_QUARTER
            }

            else -> {
                State.NO_QUARTER
            }
        }
    }
}
