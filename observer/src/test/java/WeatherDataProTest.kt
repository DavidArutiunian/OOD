import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class WeatherDataProTest {
    private val wd = WeatherDataPro()

    @Test fun `update should be called only on subscribed events`() {
        val spyDisplay = spy(Display())

        val info = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(info)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, times(1)).update(info)
    }

    @Test fun `update should be called only on subscribed events with no duplicates`() {
        val spyDisplay = spy(Display())

        val infoTempOne = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        val infoTempTwo = WeatherInfo(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(infoTempOne)
        doNothing().`when`(spyDisplay).update(infoTempTwo)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        wd.setMeasurements(2.0, 0.0, 0.0, WindInfo(0.0, 0))
        wd.setMeasurements(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, times(1)).update(infoTempTwo)
    }


    @Test fun `should not get event if stopped listening to it`() {
        val spyDisplay = spy(Display())

        val infoTempOne = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        val infoTempTwo = WeatherInfo(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(infoTempOne)
        doNothing().`when`(spyDisplay).update(infoTempTwo)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, atLeastOnce()).update(infoTempOne)

        wd.stopListeningEvent(spyDisplay, WeatherInfoType.PRESSURE)

        wd.setMeasurements(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, atLeastOnce()).update(infoTempTwo)
    }

    @Test fun `should not update if subscribed to different event`() {
        val spyDisplay = spy(Display())

        val info = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(info)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.PRESSURE)

        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, times(0)).update(info)
    }

    @Test fun `should be able to subscribe on wind change`() {
        val spyDisplay = spy(Display())

        val infoWind = WeatherInfo(0.0, 0.0, 0.0, WindInfo(1.0, 180))
        val infoTemp = WeatherInfo(1.0, 0.0, 0.0, WindInfo(1.0, 180))

        doNothing().`when`(spyDisplay).update(infoWind)
        doNothing().`when`(spyDisplay).update(infoTemp)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.WIND)

        wd.setMeasurements(0.0, 0.0, 0.0, WindInfo(1.0, 180))
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(1.0, 180))

        verify(spyDisplay, times(1)).update(infoWind)
    }
}