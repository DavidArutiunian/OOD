import java.util.*

class WeatherDataPro(
    private val mDataStrategy: ProChangeDataStrategy<WeatherInfo>,
    comparator: Comparator<Observer<*, *>>? = null
) :
    WeatherData(mDataStrategy, comparator) {
    private var mWindInfo: WindInfo? = null

    private fun getWindInfo(): WindInfo? = mWindInfo

    fun setMeasurements(temp: Double, humidity: Double, pressure: Double, wind: WindInfo) {
        mWindInfo = wind
        super.setMeasurements(temp, humidity, pressure)
    }

    override fun getChangedData(): WeatherInfo =
        mDataStrategy.getChangedData(getTemperature(), getHumidity(), getPressure(), getWindInfo())
}