import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class DuoWeatherDataTest {
    private val wdIn = WeatherData()
    private val wdOut = WeatherData()

    @Test fun `displays should be called from both IN and OUT stations`() {
        val spyDisplay = spy(Display(wdIn, wdOut))
        val spyStatsDisplay = spy(StatsDisplay(wdOut, wdOut))

        val inInfo = WeatherInfo(0.0, 0.0, 0.0, WindInfo(0.0, 0))
        val outInfo = WeatherInfo(0.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(inInfo, wdIn)
        doNothing().`when`(spyDisplay).update(inInfo, wdOut)
        doNothing().`when`(spyStatsDisplay).update(outInfo, wdOut)
        doNothing().`when`(spyStatsDisplay).update(outInfo, wdOut)

        wdIn.registerObserver(spyDisplay)
        wdIn.registerObserver(spyStatsDisplay)
        wdOut.registerObserver(spyDisplay)
        wdOut.registerObserver(spyStatsDisplay)

        wdIn.listenToEvent(spyDisplay, WeatherInfoType.ANY)
        wdOut.listenToEvent(spyDisplay, WeatherInfoType.ANY)
        wdIn.listenToEvent(spyStatsDisplay, WeatherInfoType.ANY)
        wdOut.listenToEvent(spyStatsDisplay, WeatherInfoType.ANY)

        wdIn.setMeasurements(0.0, 0.0, 0.0)
        wdOut.setMeasurements(0.0, 0.0, 0.0)

        verify(spyDisplay).update(inInfo, wdIn)
        verify(spyStatsDisplay).update(outInfo, wdOut)
    }
}