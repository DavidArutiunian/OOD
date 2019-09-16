import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import java.text.DecimalFormat
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

open class StatsDisplay(
    weatherDataIn: Observable<WeatherInfo, *>?, weatherDataOut: Observable<WeatherInfo, *>?
) : DisplayImpl(weatherDataIn, weatherDataOut) {
    private val df = DecimalFormat("0.00")

    private val mTempStats = Stats("Temp")
    private val mHumStats = Stats("Hum")
    private val mPressureStats = Stats("Pressure")
    private val mWindSpeedStats = Stats("Wind Speed")
    private val mWindDirectionStats = DirectionStats("Wind Direction")

    override fun update(data: WeatherInfo, currentWeatherData: Observable<WeatherInfo, *>) {
        if (isNotSubscribedTo(currentWeatherData)) {
            return
        }

        mTempStats.update(data.temperature)
        mHumStats.update(data.humidity)
        mPressureStats.update(data.pressure)
        mWindSpeedStats.update(data.wind.speed)
        mWindDirectionStats.update(data.wind.direction)

        print(mTempStats)
        print(mHumStats)
        print(mPressureStats)
        if (isWeatherDataOut(currentWeatherData)) {
            print(mWindSpeedStats)
            print(mWindDirectionStats)
        }
        printEnd()
    }

    private fun printAvg(stats: StatsWithAverage) {
        println("Average ${stats.name()} ${df.format(stats.avg())}")
    }

    private fun print(stats: Stats) {
        println("Max ${stats.name()} ${stats.max()}")
        println("Min ${stats.name()} ${stats.min()}")
        printAvg(stats)
    }

    private fun print(stats: DirectionStats) {
        printAvg(stats)
    }

    private fun printEnd() = println("----------------")

    private interface StatsWithAverage {
        fun name(): String

        fun avg(): Double
    }

    private abstract inner class BaseStats(private val mName: String) : StatsWithAverage {
        override fun name() = mName
    }

    private inner class Stats(
        mName: String,
        private var mMin: Double = Double.POSITIVE_INFINITY,
        private var mMax: Double = Double.NEGATIVE_INFINITY,
        private var mAcc: Double = 0.0,
        private var mAccCount: Int = 0
    ) : BaseStats(mName) {

        fun max() = mMax

        fun min() = mMin

        override fun avg() = mAcc / mAccCount

        fun update(value: Double) {
            mMin = minOf(mMin, value)
            mMax = maxOf(mMax, value)
            mAcc += value
            ++mAccCount
        }
    }

    private inner class DirectionStats(mName: String) : BaseStats(mName) {
        private var mXDir: Double = 0.0
        private var mYDir: Double = 0.0

        fun update(direction: Short) {
            mXDir += sin(toRadians(direction.toDouble()))
            mYDir += cos(toRadians(direction.toDouble()))
        }

        override fun avg(): Double = (toDegrees(atan2(mXDir, mYDir)) + 360.0) % 360.0
    }
}
