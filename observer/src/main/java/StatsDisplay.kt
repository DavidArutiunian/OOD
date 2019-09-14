open class StatsDisplay(
    weatherDataIn: Observable<WeatherInfo, *>?, weatherDataOut: Observable<WeatherInfo, *>?
) : DisplayImpl(weatherDataIn, weatherDataOut) {
    private val mTempStats = Stats("Temp")
    private val mHumStats = Stats("Hum")
    private val mPressureStats = Stats("Pressure")

    override fun update(data: WeatherInfo, currentWeatherData: Observable<WeatherInfo, *>) {
        if (isNotSubscribedTo(currentWeatherData)) {
            return
        }

        mTempStats += data.temperature
        mHumStats += data.humidity
        mPressureStats += data.pressure

        print(mTempStats)
        print(mHumStats)
        print(mPressureStats)
    }

    private fun print(stats: Stats) {
        println("Max ${stats.name()} ${stats.max()}")
        println("Min ${stats.name()} ${stats.min()}")
        println("Average ${stats.name()} ${stats.avg()}")
        println("----------------")
    }

    private inner class Stats(
        private val mName: String,
        private var mMin: Double = Double.POSITIVE_INFINITY,
        private var mMax: Double = Double.NEGATIVE_INFINITY,
        private var mAcc: Double = 0.0,
        private var mAccCount: Int = 0
    ) {
        fun name() = mName

        fun max() = mMax

        fun min() = mMin

        fun avg() = mAcc / mAccCount

        operator fun plusAssign(value: Double) {
            mMin = minOf(mMin, value)
            mMax = maxOf(mMax, value)
            mAcc += value
            ++mAccCount
        }
    }
}
