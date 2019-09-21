import org.junit.jupiter.api.Test
import org.mockito.Mockito

class RxDuoWeatherDataText {
    private val wdIn = RxWeatherDataPro()
    private val wdOut = RxWeatherDataPro()

    @Test fun `displays should be called from both IN and OUT stations`() {
        val spyDisplay = Mockito.spy(Display(wdIn, wdOut))
        val spyStatsDisplay = Mockito.spy(StatsDisplay(wdOut, wdOut))

        val inInfo = WeatherInfo(0.0, 0.0, 0.0, WindInfo(0.0, 0))
        val outInfo = WeatherInfo(0.0, 0.0, 0.0, WindInfo(0.0, 0))

        Mockito.doNothing().`when`(spyDisplay).update(inInfo, wdIn)
        Mockito.doNothing().`when`(spyDisplay).update(inInfo, wdOut)
        Mockito.doNothing().`when`(spyStatsDisplay).update(outInfo, wdOut)
        Mockito.doNothing().`when`(spyStatsDisplay).update(outInfo, wdOut)

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

        Mockito.verify(spyDisplay).update(inInfo, wdIn)
        Mockito.verify(spyStatsDisplay).update(outInfo, wdOut)
    }
}