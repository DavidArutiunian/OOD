open class StatsDisplay(priority: Int = 0) : ObserverImpl<WeatherInfo>(priority) {
    private var mMinTemperature = Double.POSITIVE_INFINITY
    private var mMaxTemperature = Double.NEGATIVE_INFINITY
    private var mAccTemperature = 0.0
    private var mCountAcc = 0.0

    override fun update(data: WeatherInfo) {
        if (mMinTemperature > data.temperature) {
            mMinTemperature = data.temperature
        }
        if (mMaxTemperature < data.temperature) {
            mMaxTemperature = data.temperature
        }
        mAccTemperature += data.temperature
        ++mCountAcc

        println("Max Temp $mMaxTemperature")
        println("Min Temp $mMinTemperature")
        val avgTemperature = (mAccTemperature / mCountAcc)
        println("Average Temp $avgTemperature")
        println("Current Priority $mPriority")
        println("----------------")
    }
}
