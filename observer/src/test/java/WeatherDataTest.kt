import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class WeatherDataTest {
    private val wd = WeatherData()

    @Test fun `should call display (priority = 1) first and stats display (priority = 0) second`() {
        val (spyDisplay, spyStatsDisplay) = createSpyDisplays()

        val info = getStubWeatherInfo()

        doNothing().`when`(spyDisplay).update(info, wd)
        doNothing().`when`(spyStatsDisplay).update(info, wd)

        val inOrder = inOrder(spyDisplay, spyStatsDisplay)

        wd.registerObserver(spyDisplay, 1)
        wd.registerObserver(spyStatsDisplay, 0)

        wd.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyDisplay).update(info, wd)
        inOrder.verify(spyStatsDisplay).update(info, wd)

        wd.removeObserver(spyDisplay)
        wd.removeObserver(spyStatsDisplay)
    }

    @Test fun `should call stats display (priority = 1) first and display (priority = 0) second`() {
        val (spyDisplay, spyStatsDisplay) = createSpyDisplays()

        val info = getStubWeatherInfo()

        doNothing().`when`(spyDisplay).update(info, wd)
        doNothing().`when`(spyStatsDisplay).update(info, wd)

        val inOrder = inOrder(spyDisplay, spyStatsDisplay)

        wd.registerObserver(spyDisplay, 0)
        wd.registerObserver(spyStatsDisplay, 1)

        wd.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyStatsDisplay).update(info, wd)
        inOrder.verify(spyDisplay).update(info, wd)

        wd.removeObserver(spyDisplay)
        wd.removeObserver(spyStatsDisplay)
    }

    @Test fun `should call display (priority = 0) first and stats display (priority = -1) second`() {
        val (spyDisplay, spyStatsDisplay) = createSpyDisplays()

        val info = getStubWeatherInfo()

        doNothing().`when`(spyDisplay).update(info, wd)
        doNothing().`when`(spyStatsDisplay).update(info, wd)

        val inOrder = inOrder(spyDisplay, spyStatsDisplay)

        wd.registerObserver(spyDisplay, 0)
        wd.registerObserver(spyStatsDisplay, -1)

        wd.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyDisplay).update(info, wd)
        inOrder.verify(spyStatsDisplay).update(info, wd)

        wd.removeObserver(spyDisplay)
        wd.removeObserver(spyStatsDisplay)
    }

    @Test fun `should call in register order if priority is the same`() {
        val (spyDisplay, spyStatsDisplay) = createSpyDisplays()

        val info = getStubWeatherInfo()

        doNothing().`when`(spyDisplay).update(info, wd)
        doNothing().`when`(spyStatsDisplay).update(info, wd)

        val inOrder = inOrder(spyDisplay, spyStatsDisplay)

        wd.registerObserver(spyDisplay)
        wd.registerObserver(spyStatsDisplay)

        wd.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyDisplay).update(info, wd)
        inOrder.verify(spyStatsDisplay).update(info, wd)

        wd.removeObserver(spyDisplay)
        wd.removeObserver(spyStatsDisplay)
    }

    private fun getStubWeatherInfo() = WeatherInfo(0.0, 0.0, 0.0, WindInfo(0.0, 0))

    private fun createSpyDisplays() = Pair(spy(Display(wd, null)), spy(StatsDisplay(wd, null)))
}