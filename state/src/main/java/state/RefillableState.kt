package state

interface RefillableState : State {
    fun refill(numBalls: Int)
}
