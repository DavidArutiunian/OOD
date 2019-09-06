import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class WeatherDataTest {
    private val observable = WeatherData(PriorityComparator())

    @Test fun `should call display (priority = 1) first and stats display (priority = 0) second`() {
        val spyDisplay = spy(Display(1))
        val spyStatsDisplay = spy(StatsDisplay(0))

        val info = WeatherInfo(0.0, 0.0, 0.0)

        doNothing().`when`(spyDisplay).update(info)
        doNothing().`when`(spyStatsDisplay).update(info)

        val inOrder = inOrder(spyDisplay, spyStatsDisplay)

        observable.registerObserver(spyDisplay)
        observable.registerObserver(spyStatsDisplay)

        observable.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyDisplay).update(info)
        inOrder.verify(spyStatsDisplay).update(info)

        observable.removeObserver(spyDisplay)
        observable.removeObserver(spyStatsDisplay)
    }

    @Test fun `should call stats display (priority = 1) first and display (priority = 0) second`() {
        val spyDisplay = spy(Display(0))
        val spyStatsDisplay = spy(StatsDisplay(1))

        val info = WeatherInfo(0.0, 0.0, 0.0)

        doNothing().`when`(spyDisplay).update(info)
        doNothing().`when`(spyStatsDisplay).update(info)

        val inOrder = inOrder(spyDisplay, spyStatsDisplay)

        observable.registerObserver(spyDisplay)
        observable.registerObserver(spyStatsDisplay)

        observable.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyStatsDisplay).update(info)
        inOrder.verify(spyDisplay).update(info)

        observable.removeObserver(spyDisplay)
        observable.removeObserver(spyStatsDisplay)
    }

    @Test fun `should call display (priority = 0) first and stats display (priority = -1) second`() {
        val spyDisplay = spy(Display(0))
        val spyStatsDisplay = spy(StatsDisplay(-1))

        val info = WeatherInfo(0.0, 0.0, 0.0)

        doNothing().`when`(spyDisplay).update(info)
        doNothing().`when`(spyStatsDisplay).update(info)

        val inOrder = inOrder(spyDisplay, spyStatsDisplay)

        observable.registerObserver(spyDisplay)
        observable.registerObserver(spyStatsDisplay)

        observable.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyDisplay).update(info)
        inOrder.verify(spyStatsDisplay).update(info)

        observable.removeObserver(spyDisplay)
        observable.removeObserver(spyStatsDisplay)
    }

    @Test fun `should call in register order if priority is the same`() {
        val spyDisplay = spy(Display())
        val spyStatsDisplay = spy(StatsDisplay())

        val info = WeatherInfo(0.0, 0.0, 0.0)

        doNothing().`when`(spyDisplay).update(info)
        doNothing().`when`(spyStatsDisplay).update(info)

        val inOrder = inOrder(spyDisplay, spyStatsDisplay)

        observable.registerObserver(spyDisplay)
        observable.registerObserver(spyStatsDisplay)

        observable.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyDisplay).update(info)
        inOrder.verify(spyStatsDisplay).update(info)

        observable.removeObserver(spyDisplay)
        observable.removeObserver(spyStatsDisplay)
    }
}