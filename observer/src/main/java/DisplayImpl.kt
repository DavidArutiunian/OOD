abstract class DisplayImpl(
    private val weatherDataIn: BaseObservable<WeatherInfo, *>?,
    private val weatherDataOut: BaseObservable<WeatherInfo, *>?
) : Observer<WeatherInfo> {
    protected fun isWeatherDataOut(currentWeatherData: BaseObservable<WeatherInfo, *>) = currentWeatherData == weatherDataOut

    protected fun isNotSubscribedTo(currentWeatherData: BaseObservable<WeatherInfo, *>) =
        currentWeatherData != weatherDataIn && currentWeatherData != weatherDataOut
}