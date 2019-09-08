import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify

internal class DouWeatherDataTest {
    private val comparator = PriorityComparator()

    private val wdIn = WeatherData(DuoWeatherChangeDataStrategy(WeatherDataType.IN), comparator)
    private val wdOut = WeatherData(DuoWeatherChangeDataStrategy(WeatherDataType.OUT), comparator)

    @Test fun `displays should be called from both IN and OUT stations`() {
        val spyDisplay = Mockito.spy(Display())
        val spyStatsDisplay = Mockito.spy(StatsDisplay())

        val inInfo = WeatherInfo(0.0, 0.0, 0.0, WeatherDataType.IN)
        val outInfo = WeatherInfo(0.0, 0.0, 0.0, WeatherDataType.OUT)

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

        verify(spyDisplay).update(inInfo)
        verify(spyDisplay).update(outInfo)
        verify(spyStatsDisplay).update(inInfo)
        verify(spyStatsDisplay).update(outInfo)
    }
}