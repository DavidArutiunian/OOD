import org.junit.jupiter.api.Test
import org.mockito.Mockito

class RxWeatherDataTest {
    private val wd = RxWeatherDataPro()

    @Test fun `should call in register order if priority is the same`() {
        val (spyDisplay, spyStatsDisplay) = createSpyDisplays()

        val info = getStubWeatherInfo()

        Mockito.doNothing().`when`(spyDisplay).update(info, wd)
        Mockito.doNothing().`when`(spyStatsDisplay).update(info, wd)

        val inOrder = Mockito.inOrder(spyDisplay, spyStatsDisplay)

        wd.registerObserver(spyDisplay)
        wd.registerObserver(spyStatsDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.ANY)
        wd.listenToEvent(spyStatsDisplay, WeatherInfoType.ANY)

        wd.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyDisplay).update(info, wd)
        inOrder.verify(spyStatsDisplay).update(info, wd)

        wd.removeObserver(spyDisplay)
        wd.removeObserver(spyStatsDisplay)
    }

    @Test fun `observer removes itself without exceptions`() {
        val observer: Observer<WeatherInfo> = Mockito.spy(object : Observer<WeatherInfo> {
            override fun update(data: WeatherInfo, currentObservable: Observable<WeatherInfo, *>) {
                currentObservable.removeObserver(this)
            }
        })

        val info = getStubWeatherInfo()

        wd.registerObserver(observer)
        wd.listenToEvent(observer, WeatherInfoType.ANY)
        wd.setMeasurements(0.0, 0.0, 0.0)

        Mockito.verify(observer).update(info, wd)

        wd.removeObserver(observer)
    }

    @Test fun `observer should not receive events if removed subscription`() {
        val (display) = createSpyDisplays()

        val infoOne = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        val infoTwo = WeatherInfo(1.0, 1.0, 0.0, WindInfo(0.0, 0))

        Mockito.doNothing().`when`(display).update(infoOne, wd)
        Mockito.doNothing().`when`(display).update(infoTwo, wd)

        wd.registerObserver(display)
        wd.listenToEvent(display, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(1.0, 0.0, 0.0)

        wd.stopListeningEvent(display, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(1.0, 1.0, 0.0)

        Mockito.verify(display).update(infoOne, wd)
        Mockito.verify(display, Mockito.never()).update(infoTwo, wd)
    }

    @Test fun `observer should not be called if not subscribed to ANY events`() {
        val (display) = createSpyDisplays()

        val info = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        Mockito.doNothing().`when`(display).update(info, wd)

        wd.registerObserver(display)

        wd.setMeasurements(1.0, 0.0, 0.0)

        Mockito.verify(display, Mockito.never()).update(info, wd)
    }

    private fun getStubWeatherInfo() = WeatherInfo(0.0, 0.0, 0.0, WindInfo(0.0, 0))

    private fun createSpyDisplays() = Pair(Mockito.spy(Display(wd, null)), Mockito.spy(StatsDisplay(wd, null)))
}