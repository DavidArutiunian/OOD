open class DuoWeatherChangeDataStrategy(private val mDataType: WeatherDataType) : ChangeDataStrategy<WeatherInfo> {
    override fun getChangedData(temperature: Double, humidity: Double, pressure: Double): WeatherInfo =
        WeatherInfo(temperature, humidity, pressure, mDataType)
}