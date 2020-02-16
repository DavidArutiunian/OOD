import gumballmachine.GumballMachine
import multigumballmachine.withstate.MultiGumballMachine
import java.util.Scanner
import kotlin.system.exitProcess

fun main() {
    val sc = createScanner()
    val numBalls = readNumBalls(sc)
    printSplash()
    val gumballMachine = createGumballMachine(numBalls)

    val commands = HashMap<String, () -> Unit>()
    commands["Exit"] = { exitProcess(0) }
    commands["InsertQuarter"] = { gumballMachine.insertQuarter() }
    commands["TurnCrank"] = { gumballMachine.turnCrank() }
    commands["EjectQuarter"] = { gumballMachine.ejectQuarter() }

    while (true) run {
        printReadToken()
        val token = readToken(sc)
        commands[token]?.invoke()
    }
}

fun printSplash() = println("===== START =====")

fun printReadToken() = print("<<: ")

fun createScanner(): Scanner = Scanner(System.`in`)

fun readToken(sc: Scanner): String = sc.next()

fun readNumBalls(sc: Scanner): Int {
    println("===== CONFIGURATION =====")
    print("<< NUM_BALLS: ")
    return sc.nextInt()
}

fun createGumballMachine(numBalls: Int): GumballMachine = MultiGumballMachine(numBalls)
