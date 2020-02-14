package gumball_machine

import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream


internal class GumballMachineImplTest {
    companion object {
        val EOLN = System.lineSeparator()!!
        const val INITIAL_NUM_BALLS = 5
    }

    private val byteArrayOutputStream = ByteArrayOutputStream()
    private val systemOutputStream = System.out
    private lateinit var gumballMachine: GumballMachine

    /**
     * Create GumballMachine with 5 balls
     */
    @BeforeEach
    fun setUp() {
        val printStream = PrintStream(byteArrayOutputStream)
        System.setOut(printStream)

        createGumballMachine(INITIAL_NUM_BALLS)
    }

    @AfterEach
    fun tearDown() {
        System.out.flush()
        System.setOut(systemOutputStream)
    }

    @Nested
    internal inner class HasQuarterStateTest {
        /**
         * Make GumballMachine to has quarter state
         */
        @BeforeEach
        fun setUp() {
            val numBalls = 1
            setUpGumballMachine(numBalls)
        }

        @Test
        fun `insert quarter`() {
            gumballMachine.insertQuarter()
            assertStringsTrimmed("You can't insert another quarter", byteArrayOutputStream.toString())
        }

        @Test
        fun `eject quarter`() {
            gumballMachine.ejectQuarter()
            assertStringsTrimmed("Quarter returned", byteArrayOutputStream.toString())
        }

        @Test
        fun `turn crank with 1 ball`() {
            gumballMachine.turnCrank()
            assertStringsTrimmed("You turned...${EOLN}" +
                "A gumball comes rolling out the slot...${EOLN}" +
                "Oops, out of gumballs", byteArrayOutputStream.toString())
        }

        @Test
        fun `turn crank with 2 balls`() {
            val numBalls = 2
            setUpGumballMachine(numBalls)
            gumballMachine.turnCrank()
            assertStringsTrimmed("You turned...${EOLN}A gumball comes rolling out the slot...", byteArrayOutputStream.toString())
        }

        private fun setUpGumballMachine(numBalls: Int = 1) {
            createGumballMachine(numBalls)
            gumballMachine.insertQuarter()
            byteArrayOutputStream.reset()
        }
    }

    @Nested
    internal inner class NoQuarterStateTest {
        @Test
        fun `insert quarter`() {
            gumballMachine.insertQuarter()
            assertStringsTrimmed("You inserted a quarter", byteArrayOutputStream.toString())
        }

        @Test
        fun `eject quarter`() {
            gumballMachine.ejectQuarter()
            assertStringsTrimmed("You haven't inserted a quarter", byteArrayOutputStream.toString())
        }

        @Test
        fun `turn crank`() {
            gumballMachine.turnCrank()
            assertStringsTrimmed("You turned but there's no quarter${EOLN}You need to pay first", byteArrayOutputStream.toString())
        }

        @Test
        fun `insert 1 quarter to machine with 1 ball and check machine empty`() {
            val numBalls = 1
            createGumballMachine(numBalls)
            gumballMachine.insertQuarter()
            gumballMachine.ejectQuarter()
            byteArrayOutputStream.reset()
            gumballMachine.turnCrank()
            assertStringsTrimmed("You turned but there's no quarter${EOLN}You need to pay first", byteArrayOutputStream.toString())
        }
    }

    @Nested
    internal inner class SoldOutStateTests {
        /**
         * Make GumballMachine to sold out state
         */
        @BeforeEach
        fun setUp() {
            val numBalls = 1
            createGumballMachine(numBalls)
            gumballMachine.insertQuarter()
            gumballMachine.turnCrank()
            byteArrayOutputStream.reset()
        }

        @Test
        fun `insert quarter`() {
            gumballMachine.insertQuarter()
            assertStringsTrimmed("You can't insert a quarter, the machine is sold out", byteArrayOutputStream.toString())
        }

        @Test
        fun `eject quarter`() {
            gumballMachine.ejectQuarter()
            assertStringsTrimmed("You can't eject, you haven't inserted a quarter yet", byteArrayOutputStream.toString())
        }

        @Test
        fun `turn crank`() {
            gumballMachine.turnCrank()
            assertStringsTrimmed("You turned but there're no gumballs${EOLN}No gumball dispensed", byteArrayOutputStream.toString())
        }
    }

    private fun assertStringsTrimmed(expected: String, actual: String) {
        Assertions.assertEquals(expected.trim(), actual.trim())
    }

    private fun createGumballMachine(numBalls: Int) {
        gumballMachine = GumballMachineImpl(numBalls)
    }
}
