class WeatherDataPro : WeatherData() {
    private var mWindInfo = WindInfo(0.0, 0)

    private fun getWindInfo() = mWindInfo

    fun setMeasurements(temp: Double, humidity: Double, pressure: Double, wind: WindInfo) {
        val events = mutableSetOf<WeatherInfoType>()

        when {
            changed(mWindInfo, wind) -> events.add(WeatherInfoType.WIND)
        }

        mWindInfo = wind

        setMeasurementsImpl(temp, humidity, pressure, events)
    }

    override fun getChangedData() = WeatherInfo(getTemperature(), getHumidity(), getPressure(), getWindInfo())
}