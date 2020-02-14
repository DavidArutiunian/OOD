package gumballmachine

interface GumballMachineInternal {
    fun releaseBall()

    fun getBallCount(): Int

    fun setSoldOutState()

    fun setNoQuarterState()

    fun setSoldState()

    fun setHasQuarterState()
}
