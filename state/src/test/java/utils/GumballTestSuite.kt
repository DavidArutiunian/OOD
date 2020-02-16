package utils

import gumballmachine.GumballMachine
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.PrintStream

abstract class GumballTestSuite {
    companion object {
        val EOLN = System.lineSeparator()!!
        const val INITIAL_NUM_BALLS = 5
    }

    protected val byteArrayOutputStream = ByteArrayOutputStream()
    private val systemOutputStream = System.out
    protected lateinit var gumballMachine: GumballMachine

    @BeforeEach
    fun setUp() {
        val printStream = PrintStream(byteArrayOutputStream)
        System.setOut(printStream)

        gumballMachine = createGumballMachine(INITIAL_NUM_BALLS)
    }

    @AfterEach
    fun tearDown() {
        System.out.flush()
        System.setOut(systemOutputStream)
    }

    protected fun assertOutputStream(expected: String) {
        assertStringsTrimmed(expected, byteArrayOutputStream.toString())
    }

    private fun assertStringsTrimmed(expected: String, actual: String) {
        Assertions.assertEquals(expected.trim(), actual.trim())
    }

    protected abstract fun createGumballMachine(numBalls: Int): GumballMachine
}
