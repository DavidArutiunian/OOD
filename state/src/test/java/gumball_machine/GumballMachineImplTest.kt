package gumball_machine

import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream


internal class GumballMachineImplTest {
    companion object {
        val EOLN = System.lineSeparator()!!
    }

    private val byteArrayOutputStream = ByteArrayOutputStream()
    private val systemOutputStream = System.out
    private lateinit var gumballMachine: GumballMachine

    @BeforeEach
    fun setUp() {
        val printStream = PrintStream(byteArrayOutputStream)
        System.setOut(printStream)

        gumballMachine = GumballMachineImpl(5)
    }

    @AfterEach
    fun tearDown() {
        System.out.flush()
        System.setOut(systemOutputStream)
    }

    @Nested
    internal inner class NoQuarterStateTest {
        @Test
        fun insertQuarter() {
            gumballMachine.insertQuarter()
            assertStringsTrimmed("You inserted a quarter", byteArrayOutputStream.toString())
        }

        @Test
        fun ejectQuarter() {
            gumballMachine.ejectQuarter()
            assertStringsTrimmed("You haven't inserted a quarter", byteArrayOutputStream.toString())
        }

        @Test
        fun turnCrank() {
            gumballMachine.turnCrank()
            assertStringsTrimmed("You turned but there's no quarter${EOLN}You need to pay first", byteArrayOutputStream.toString())
        }

        @Test
        fun insertAndEjectQuarter() {
            gumballMachine = GumballMachineImpl(1)
            gumballMachine.insertQuarter()
            gumballMachine.ejectQuarter()
            byteArrayOutputStream.reset()
            gumballMachine.turnCrank()
            assertStringsTrimmed("You turned but there's no quarter${EOLN}You need to pay first", byteArrayOutputStream.toString())
        }
    }

    @Nested
    internal inner class SoldOutStateTests {
        @BeforeEach
        fun setUp() {
            gumballMachine = GumballMachineImpl(1)
            gumballMachine.insertQuarter()
            gumballMachine.turnCrank()
            byteArrayOutputStream.reset()
        }

        @Test
        fun insertQuarter() {
            gumballMachine.insertQuarter()
            assertStringsTrimmed("You can't insert a quarter, the machine is sold out", byteArrayOutputStream.toString())
        }

        @Test
        fun ejectQuarter() {
            gumballMachine.ejectQuarter()
            assertStringsTrimmed("You can't eject, you haven't inserted a quarter yet", byteArrayOutputStream.toString())
        }

        @Test
        fun turnCrank() {
            gumballMachine.turnCrank()
            assertStringsTrimmed("You turned but there're no gumballs${EOLN}No gumball dispensed", byteArrayOutputStream.toString())
        }
    }

    private fun assertStringsTrimmed(expected: String, actual: String) {
        Assertions.assertEquals(expected.trim(), actual.trim())
    }
}
