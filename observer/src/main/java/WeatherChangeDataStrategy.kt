class WeatherChangeDataStrategy : ChangeDataStrategy<WeatherInfo> {
    override fun getChangedData(temperature: Double, humidity: Double, pressure: Double): WeatherInfo =
        WeatherInfo(temperature, humidity, pressure)
}