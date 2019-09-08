import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class WeatherDataProTest {
    private val wd = WeatherDataPro(ProDuoWeatherChangeDataStrategy(WeatherDataType.OUT), PriorityComparator())

    @Test fun `observer should get only events it subscribed to`() {
        val spyDisplay = spy(Display())

        val info = WeatherInfo(0.0, 0.0, 0.0, WeatherDataType.OUT, WindInfo(0.0, 0))
        val events = mutableSetOf(WeatherInfoType.TEMPERATURE)

        doNothing().`when`(spyDisplay).update(info, events)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)
        wd.setMeasurements(0.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay).update(info, events)
    }

    @Test fun `should get null events if not subscribed to any`() {
        val spyDisplay = spy(Display())

        val info = WeatherInfo(0.0, 0.0, 0.0, WeatherDataType.OUT, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(info, null)

        wd.registerObserver(spyDisplay)

        wd.setMeasurements(0.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay).update(info, null)
    }

    @Test fun `should not get event if removed stopped listening to it`() {
        val spyDisplay = spy(Display())

        val info = WeatherInfo(0.0, 0.0, 0.0, WeatherDataType.OUT, WindInfo(0.0, 0))
        val events = mutableSetOf(WeatherInfoType.TEMPERATURE, WeatherInfoType.PRESSURE)

        doNothing().`when`(spyDisplay).update(info, events)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)
        wd.listenToEvent(spyDisplay, WeatherInfoType.PRESSURE)
        wd.setMeasurements(0.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, atLeastOnce()).update(info, events)


        wd.stopListeningEvent(spyDisplay, WeatherInfoType.PRESSURE)
        events.remove(WeatherInfoType.PRESSURE)

        wd.setMeasurements(0.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, atLeastOnce()).update(info, events)
    }
}