abstract class DisplayImpl(
    private val weatherDataIn: Observable<WeatherInfo, *>?,
    private val weatherDataOut: Observable<WeatherInfo, *>?
) : Observer<WeatherInfo> {
    protected fun isWeatherDataOut(currentWeatherData: Observable<WeatherInfo, *>) = currentWeatherData == weatherDataOut

    protected fun isNotSubscribedTo(currentWeatherData: Observable<WeatherInfo, *>) =
        currentWeatherData != weatherDataIn && currentWeatherData != weatherDataOut
}