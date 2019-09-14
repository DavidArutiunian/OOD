class Display(
    weatherDataIn: Observable<WeatherInfo, *>?, weatherDataOut: Observable<WeatherInfo, *>?
) : DisplayImpl(weatherDataIn, weatherDataOut) {
    override fun update(data: WeatherInfo, currentWeatherData: Observable<WeatherInfo, *>) {
        if (isNotSubscribedTo(currentWeatherData)) {
            return
        }
        println("Current Temp ${data.temperature}")
        println("Current Hum ${data.humidity}")
        println("Current Pressure ${data.pressure}")
        if (isWeatherDataOut(currentWeatherData)) {
            println("Current Wind Speed ${data.wind.speed}")
            println("Current Wind Direction ${data.wind.direction}")
        }
        println("----------------")
    }
}
