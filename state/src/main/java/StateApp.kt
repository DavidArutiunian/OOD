import gumballmachine.GumballMachine
import gumballmachine.GumballMachineImpl

fun <T : GumballMachine> testGumballMachine(m: T) {
    println(m.toString())

    m.insertQuarter()
    m.turnCrank()

    println(m.toString())

    m.insertQuarter()
    m.turnCrank()
    m.insertQuarter()
    m.turnCrank()
    m.ejectQuarter()

    println(m.toString())

    m.insertQuarter()
    m.insertQuarter()
    m.turnCrank()
    m.insertQuarter()
    m.turnCrank()
    m.insertQuarter()
    m.turnCrank()

    println(m.toString())
}

fun main() {
    val m = GumballMachineImpl(5)
    testGumballMachine(m)
}
