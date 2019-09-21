import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class RxWeatherDataProTest {
    private val wd = RxWeatherDataPro()

    @Test fun `update should be called only on subscribed events`() {
        val spyDisplay = createSpyDisplay()

        val infoOne = WeatherInfo(1.0, 0.0, 1.0, WindInfo(0.0, 0))
        val infoTwo = WeatherInfo(1.0, 0.0, 2.0, WindInfo(0.0, 0))

        Mockito.doNothing().`when`(spyDisplay).update(infoOne, wd)
        Mockito.doNothing().`when`(spyDisplay).update(infoTwo, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)
        wd.listenToEvent(spyDisplay, WeatherInfoType.PRESSURE)

        wd.setMeasurements(1.0, 0.0, 1.0, WindInfo(0.0, 0))
        wd.setMeasurements(1.0, 0.0, 2.0, WindInfo(0.0, 0))

        Mockito.verify(spyDisplay).update(infoOne, wd)
        Mockito.verify(spyDisplay).update(infoTwo, wd)
    }

    @Test fun `update should be called only on subscribed events with no duplicates`() {
        val spyDisplay = createSpyDisplay()

        val infoTempOne = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        val infoTempTwo = WeatherInfo(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        Mockito.doNothing().`when`(spyDisplay).update(infoTempOne, wd)
        Mockito.doNothing().`when`(spyDisplay).update(infoTempTwo, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        wd.setMeasurements(2.0, 0.0, 0.0, WindInfo(0.0, 0))
        wd.setMeasurements(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        Mockito.verify(spyDisplay, Mockito.times(1)).update(infoTempTwo, wd)
    }


    @Test fun `should not get event if stopped listening to it`() {
        val spyDisplay = createSpyDisplay()

        val infoTempOne = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))
        val infoTempTwo = WeatherInfo(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        Mockito.doNothing().`when`(spyDisplay).update(infoTempOne, wd)
        Mockito.doNothing().`when`(spyDisplay).update(infoTempTwo, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.TEMPERATURE)
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        Mockito.verify(spyDisplay, Mockito.atLeastOnce()).update(infoTempOne, wd)

        wd.stopListeningEvent(spyDisplay, WeatherInfoType.TEMPERATURE)

        wd.setMeasurements(2.0, 0.0, 0.0, WindInfo(0.0, 0))

        Mockito.verify(spyDisplay, Mockito.never()).update(infoTempTwo, wd)
    }

    @Test fun `should not update if subscribed to different event`() {
        val spyDisplay = createSpyDisplay()

        val info = WeatherInfo(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        Mockito.doNothing().`when`(spyDisplay).update(info, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.PRESSURE)

        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(0.0, 0))

        Mockito.verify(spyDisplay, Mockito.times(0)).update(info, wd)
    }

    @Test fun `should be able to subscribe on wind change`() {
        val spyDisplay = createSpyDisplay()

        val infoWind = WeatherInfo(0.0, 0.0, 0.0, WindInfo(1.0, 180))
        val infoTemp = WeatherInfo(1.0, 0.0, 0.0, WindInfo(1.0, 180))

        Mockito.doNothing().`when`(spyDisplay).update(infoWind, wd)
        Mockito.doNothing().`when`(spyDisplay).update(infoTemp, wd)

        wd.registerObserver(spyDisplay)

        wd.listenToEvent(spyDisplay, WeatherInfoType.WIND)

        wd.setMeasurements(0.0, 0.0, 0.0, WindInfo(1.0, 180))
        wd.setMeasurements(1.0, 0.0, 0.0, WindInfo(1.0, 180))

        Mockito.verify(spyDisplay, Mockito.times(1)).update(infoWind, wd)
    }

    private fun createSpyDisplay() = Mockito.spy(Display(wd, null))
}