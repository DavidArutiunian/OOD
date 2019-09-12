import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class DouWeatherDataTest {
    private val wdIn = WeatherData()
    private val wdOut = WeatherData()

    @Test fun `displays should be called from both IN and OUT stations`() {
        val spyDisplay = spy(Display())
        val spyStatsDisplay = spy(StatsDisplay())

        val inInfo = WeatherInfo(0.0, 0.0, 0.0, WindInfo(0.0, 0))
        val outInfo = WeatherInfo(0.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(inInfo)
        doNothing().`when`(spyDisplay).update(outInfo)
        doNothing().`when`(spyStatsDisplay).update(inInfo)
        doNothing().`when`(spyStatsDisplay).update(outInfo)

        wdIn.registerObserver(spyDisplay)
        wdIn.registerObserver(spyStatsDisplay)
        wdOut.registerObserver(spyDisplay)
        wdOut.registerObserver(spyStatsDisplay)

        wdIn.setMeasurements(0.0, 0.0, 0.0)
        wdOut.setMeasurements(0.0, 0.0, 0.0)

        // TODO: everyone of these should be called once
        // @see https://github.com/DavidArutiunian/OOD/issues/4#issuecomment-529330339
        verify(spyDisplay, atLeastOnce()).update(inInfo)
        verify(spyDisplay, atLeastOnce()).update(outInfo)
        verify(spyStatsDisplay, atLeastOnce()).update(inInfo)
        verify(spyStatsDisplay, atLeastOnce()).update(outInfo)
    }
}