import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class RxWeatherDataProTest {
    private val wd = RxWeatherDataPro()

    @Test fun `update should be called only on subscribed events`() {
        val spyDisplay = createSpyDisplay()

        val infoOne = WeatherInfo(1.0, 0.0, 1.0, WindInfo(0.0, 0))
        val infoTwo = WeatherInfo(1.0, 0.0, 2.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(infoOne, wd)
        doNothing().`when`(spyDisplay).update(infoTwo, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)
        wd.listenToEvent(spyDisplay, WeatherInfoType.PRESSURE)

        wd.setMeasurements(1.0, 0.0, 1.0, WindInfo(0.0, 0))
        wd.setMeasurements(1.0, 0.0, 2.0, WindInfo(0.0, 0))

        verify(spyDisplay).update(infoOne, wd)
        verify(spyDisplay).update(infoTwo, wd)
    }

    @Test fun `update should be called only on subscribed events with no duplicates`() {
        val spyDisplay = createSpyDisplay()

        val infoTempOne = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        val infoTempTwo = WeatherInfo(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(infoTempOne, wd)
        doNothing().`when`(spyDisplay).update(infoTempTwo, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        wd.setMeasurements(2.0, 0.0, 0.0, WindInfo(0.0, 0))
        wd.setMeasurements(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, times(1)).update(infoTempTwo, wd)
    }


    @Test fun `should not get event if stopped listening to it`() {
        val spyDisplay = createSpyDisplay()

        val infoTempOne = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        val infoTempTwo = WeatherInfo(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(infoTempOne, wd)
        doNothing().`when`(spyDisplay).update(infoTempTwo, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, atLeastOnce()).update(infoTempOne, wd)

        wd.stopListeningEvent(spyDisplay, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, never()).update(infoTempTwo, wd)
    }

    @Test fun `should not update if subscribed to different event`() {
        val spyDisplay = createSpyDisplay()

        val info = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        doNothing().`when`(spyDisplay).update(info, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.PRESSURE)

        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        verify(spyDisplay, times(0)).update(info, wd)
    }

    @Test fun `should be able to subscribe on wind change`() {
        val spyDisplay = createSpyDisplay()

        val infoWind = WeatherInfo(0.0, 0.0, 0.0, WindInfo(1.0, 180))
        val infoTemp = WeatherInfo(1.0, 0.0, 0.0, WindInfo(1.0, 180))

        doNothing().`when`(spyDisplay).update(infoWind, wd)
        doNothing().`when`(spyDisplay).update(infoTemp, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.WIND)

        wd.setMeasurements(0.0, 0.0, 0.0, WindInfo(1.0, 180))
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(1.0, 180))

        verify(spyDisplay, times(1)).update(infoWind, wd)
    }

    private fun createSpyDisplay() = spy(Display(wd, null))
}