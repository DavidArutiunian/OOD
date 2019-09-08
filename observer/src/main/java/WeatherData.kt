import java.util.*

open class WeatherData(
    private val mDataStrategy: ChangeDataStrategy<WeatherInfo>,
    comparator: Comparator<Observer<*, *>>? = null
) : ObservableImpl<WeatherInfo, WeatherInfoType>(comparator) {
    private var mTemperature = 0.0
    private var mHumidity = 0.0
    private var mPressure = 0.0

    protected fun getTemperature() = mTemperature

    protected fun getHumidity() = mHumidity

    protected fun getPressure() = mPressure

    private fun measurementsChanged() {
        notifyObservers()
    }

    fun setMeasurements(temp: Double, humidity: Double, pressure: Double) {
        mTemperature = temp
        mHumidity = humidity
        mPressure = pressure

        measurementsChanged()
    }

    override fun getChangedData() = mDataStrategy.getChangedData(getTemperature(), getHumidity(), getPressure())
}
