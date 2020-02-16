package multigumballmachine.naive

import gumballmachine.GumballMachine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.GumballTestSuite

internal class MultiGumballMachineImplTest : GumballTestSuite() {
    @Nested
    internal inner class SoldStateTest {
        /**
         * Make GumballMachine to sold state
         */
        @BeforeEach
        fun setUp() {
            val numBalls = 2
            createGumballMachine(numBalls)
            gumballMachine.insertQuarter()
            gumballMachine.turnCrank()
            byteArrayOutputStream.reset()
        }

        @Test
        fun `insert quarter`() {
            gumballMachine.insertQuarter()
            assertOutputStream("You inserted 1 quarter")
        }

        @Test
        fun `insert 2 quarters`() {
            gumballMachine.insertQuarter()
            byteArrayOutputStream.reset()
            gumballMachine.insertQuarter()
            assertOutputStream("You inserted 2 quarters")
        }

        @Test
        fun `eject quarter`() {
            gumballMachine.ejectQuarter()
            assertOutputStream("You haven't inserted a quarter")
        }

        @Test
        fun `turn crank`() {
            gumballMachine.turnCrank()
            assertOutputStream("You turned but there's no quarter${EOLN}You need to pay first")
        }
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
            assertOutputStream("You inserted 2 quarters")
        }

        @Test
        fun `eject quarter`() {
            gumballMachine.ejectQuarter()
            assertOutputStream("All quarters returned")
        }

        @Test
        fun `turn crank with 1 ball`() {
            gumballMachine.turnCrank()
            assertOutputStream(
                "You turned...${EOLN}" +
                    "A gumball comes rolling out the slot...${EOLN}" +
                    "Oops, out of gumballs"
            )
        }

        @Test
        fun `turn crank with 2 balls`() {
            val numBalls = 2
            setUpGumballMachine(numBalls)
            gumballMachine.turnCrank()
            assertOutputStream("You turned...${EOLN}A gumball comes rolling out the slot...")
        }

        private fun setUpGumballMachine(numBalls: Int = 1) {
            gumballMachine = createGumballMachine(numBalls)
            gumballMachine.insertQuarter()
            byteArrayOutputStream.reset()
        }
    }

    @Nested
    internal inner class NoQuarterStateTest {
        @Test
        fun `insert quarter`() {
            gumballMachine.insertQuarter()
            assertOutputStream("You inserted 1 quarter")
        }

        @Test
        fun `eject quarter`() {
            gumballMachine.ejectQuarter()
            assertOutputStream("You haven't inserted a quarter")
        }

        @Test
        fun `turn crank`() {
            gumballMachine.turnCrank()
            assertOutputStream("You turned but there's no quarter${EOLN}You need to pay first")
        }

        @Test
        fun `insert 1 quarter to machine with 1 ball and check machine empty`() {
            val numBalls = 1
            createGumballMachine(numBalls)
            gumballMachine.insertQuarter()
            gumballMachine.ejectQuarter()
            byteArrayOutputStream.reset()
            gumballMachine.turnCrank()
            assertOutputStream("You turned but there's no quarter${EOLN}You need to pay first")
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
            gumballMachine = createGumballMachine(numBalls)
            gumballMachine.insertQuarter()
            gumballMachine.turnCrank()
            byteArrayOutputStream.reset()
        }

        @Test
        fun `insert quarter`() {
            gumballMachine.insertQuarter()
            assertOutputStream("You can't insert a quarter, the machine is sold out")
        }

        @Test
        fun `eject quarter`() {
            gumballMachine.ejectQuarter()
            assertOutputStream("You can't eject, you haven't inserted a quarter yet")
        }

        @Test
        fun `turn crank`() {
            gumballMachine.turnCrank()
            assertOutputStream("You turned but there're no gumballs${EOLN}No gumball dispensed")
        }
    }

    override fun createGumballMachine(numBalls: Int): GumballMachine {
        return MultiGumballMachine(numBalls)
    }
}
