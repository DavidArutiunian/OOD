package gumball_machine

interface GumballMachine {
    fun ejectQuarter()

    fun insertQuarter()

    fun turnCrank()

    fun releaseBall()

    fun getBallCount(): Int

    fun setSoldOutState()

    fun setNoQuarterState()

    fun setSoldState()

    fun setHasQuarterState()
}
