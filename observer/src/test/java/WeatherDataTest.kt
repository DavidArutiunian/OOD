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

        wd.listenToEvent(spyDisplay, WeatherInfoType.ANY)
        wd.listenToEvent(spyStatsDisplay, WeatherInfoType.ANY)

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

        wd.listenToEvent(spyDisplay, WeatherInfoType.ANY)
        wd.listenToEvent(spyStatsDisplay, WeatherInfoType.ANY)

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

        wd.listenToEvent(spyDisplay, WeatherInfoType.ANY)
        wd.listenToEvent(spyStatsDisplay, WeatherInfoType.ANY)

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

        wd.listenToEvent(spyDisplay, WeatherInfoType.ANY)
        wd.listenToEvent(spyStatsDisplay, WeatherInfoType.ANY)

        wd.setMeasurements(0.0, 0.0, 0.0)

        inOrder.verify(spyDisplay).update(info, wd)
        inOrder.verify(spyStatsDisplay).update(info, wd)

        wd.removeObserver(spyDisplay)
        wd.removeObserver(spyStatsDisplay)
    }

    @Test fun `observer removes itself without exceptions`() {
        val observer: Observer<WeatherInfo> = spy(object : Observer<WeatherInfo> {
            override fun update(data: WeatherInfo, currentObservable: Observable<WeatherInfo, *>) {
                currentObservable.removeObserver(this)
            }
        })

        val info = getStubWeatherInfo()

        wd.registerObserver(observer)
        wd.listenToEvent(observer, WeatherInfoType.ANY)
        wd.setMeasurements(0.0, 0.0, 0.0)

        verify(observer).update(info, wd)

        wd.removeObserver(observer)
    }

    @Test fun `observer should not receive events if removed subscription`() {
        val (display) = createSpyDisplays()

        val infoOne = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        val infoTwo = WeatherInfo(1.0, 1.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(display).update(infoOne, wd)
        doNothing().`when`(display).update(infoTwo, wd)

        wd.registerObserver(display)
        wd.listenToEvent(display, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(1.0, 0.0, 0.0)

        wd.stopListeningEvent(display, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(1.0, 1.0, 0.0)

        verify(display).update(infoOne, wd)
        verify(display, never()).update(infoTwo, wd)
    }

    @Test fun `observer should not be called if not subscribed to ANY events`() {
        val (display) = createSpyDisplays()

        val info = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(display).update(info, wd)

        wd.registerObserver(display)

        wd.setMeasurements(1.0, 0.0, 0.0)

        verify(display, never()).update(info, wd)
    }

    private fun getStubWeatherInfo() = WeatherInfo(0.0, 0.0, 0.0, WindInfo(0.0, 0))

    private fun createSpyDisplays() = Pair(spy(Display(wd, null)), spy(StatsDisplay(wd, null)))
}