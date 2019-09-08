class ProDuoWeatherChangeDataStrategy(private val mDataType: WeatherDataType) :
    DuoWeatherChangeDataStrategy(mDataType), ProChangeDataStrategy<WeatherInfo> {
    override fun getChangedData(temperature: Double, humidity: Double, pressure: Double, wind: WindInfo?): WeatherInfo =
        WeatherInfo(temperature, humidity, pressure, mDataType, wind)
}