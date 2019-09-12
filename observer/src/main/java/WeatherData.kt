open class WeatherData : ObservableImpl<WeatherInfo, WeatherInfoType>() {
    private var mTemperature = 0.0
    private var mHumidity = 0.0
    private var mPressure = 0.0

    protected fun getTemperature() = mTemperature

    protected fun getHumidity() = mHumidity

    protected fun getPressure() = mPressure

    protected fun changed(prev: Any, curr: Any) = prev != curr

    private fun measurementsChanged(events: Set<WeatherInfoType>) = notifyObservers(events)

    protected fun setMeasurementsImpl(temp: Double, humidity: Double, pressure: Double, events: MutableSet<WeatherInfoType>) {
        when {
            changed(mTemperature, temp) -> events.add(WeatherInfoType.TEMPERATURE)
            changed(mHumidity, temp) -> events.add(WeatherInfoType.HUMIDITY)
            changed(mPressure, pressure) -> events.add(WeatherInfoType.PRESSURE)
        }

        mTemperature = temp
        mHumidity = humidity
        mPressure = pressure

        measurementsChanged(events)
    }

    fun setMeasurements(temp: Double, humidity: Double, pressure: Double) {
        val events = mutableSetOf<WeatherInfoType>()
        setMeasurementsImpl(temp, humidity, pressure, events)
    }

    override fun getChangedData() = WeatherInfo(getTemperature(), getHumidity(), getPressure(), WindInfo(0.0, 0))
}
