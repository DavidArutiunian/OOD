import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class RxWeatherDataTest {
    @Test fun `should subscribe to wd`() {
        val wd = RxWeatherData()

        val spyTempDisplay = spy { temp: Double -> println(temp) }
        val spyPressureDisplay = spy { pressure: Double -> println(pressure) }

        doNothing().`when`(spyTempDisplay).invoke(0.0)
        doNothing().`when`(spyPressureDisplay).invoke(0.0)

        wd.doOnHumidityChange(spyTempDisplay)
        wd.changeTemperature(0.0)
        wd.changeHumidity(0.0)

        verify(spyTempDisplay, times(1)).invoke(0.0)
        verify(spyPressureDisplay, times(0)).invoke(0.0)
    }

    @Test fun `should dispose subscription`() {
        val wd = RxWeatherData()

        val spyTempDisplay = spy { temp: Double -> println(temp) }
        doNothing().`when`(spyTempDisplay).invoke(0.0)
        doNothing().`when`(spyTempDisplay).invoke(1.0)

        val conn = wd.doOnTemperatureChange(spyTempDisplay)
        wd.changeTemperature(0.0)

        conn.dispose()

        wd.changeTemperature(1.0)

        verify(spyTempDisplay, times(1)).invoke(0.0)
        verify(spyTempDisplay, times(0)).invoke(1.0)
    }
}
