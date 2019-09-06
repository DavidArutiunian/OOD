import java.util.Comparator

class WeatherData(comparator: Comparator<Observer<*>>? = null) : ObservableImpl<WeatherInfo>(comparator) {
    private var mTemperature = 0.0
    private var mHumidity = 0.0
    private var mPressure = 0.0

    private fun getTemperature() = mTemperature

    private fun getHumidity() = mHumidity

    private fun getPressure() = mPressure

    private fun measurementsChanged() {
        notifyObservers()
    }

    fun setMeasurements(temp: Double, humidity: Double, pressure: Double) {
        mTemperature = temp
        mHumidity = humidity
        mPressure = pressure

        measurementsChanged()
    }

    override fun getChangedData() = WeatherInfo(getTemperature(), getHumidity(), getPressure())
}
