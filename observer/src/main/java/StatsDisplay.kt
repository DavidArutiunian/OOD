open class StatsDisplay(priority: Int = 0) : ObserverImpl<WeatherInfo, WeatherInfoType>(priority) {
    private var mMinTemperature = Double.POSITIVE_INFINITY
    private var mMaxTemperature = Double.NEGATIVE_INFINITY
    private var mAccTemperature = 0.0
    private var mAccWindSpeed = 0.0
    private var mAccWindDirection = 0.0
    private var mCountAcc = 0.0

    override fun update(data: WeatherInfo, events: Set<WeatherInfoType>?) {
        if (mMinTemperature > data.temperature) {
            mMinTemperature = data.temperature
        }
        if (mMaxTemperature < data.temperature) {
            mMaxTemperature = data.temperature
        }
        mAccTemperature += data.temperature
        if (data.wind != null) {
            mAccWindSpeed += data.wind.speed
            mAccWindDirection += data.wind.direction
        }

        ++mCountAcc

        val avgTemperature = (mAccTemperature / mCountAcc)
        val avgWindSpeed = mAccWindSpeed / mCountAcc
        val avgWindDirection = mAccWindDirection / mCountAcc

        if (events != null) {
            events.forEach {
                when (it) {
                    WeatherInfoType.TEMPERATURE -> {
                        println("Max Temp $mMaxTemperature")
                        println("Min Temp $mMinTemperature")
                        println("Average Temp $avgTemperature")
                    }
                    WeatherInfoType.WIND -> if (data.wind != null) {
                        println("Average Wind Speed $avgWindSpeed")
                        println("Average Wind Direction $avgWindDirection")
                    }
                }
            }
        } else {
            println("Max Temp $mMaxTemperature")
            println("Min Temp $mMinTemperature")
            println("Average Temp $avgTemperature")
            if (data.wind != null) {
                println("Average Wind Speed $avgWindSpeed")
                println("Average Wind Direction $avgWindDirection")
            }
        }
        println("Current Priority ${getPriority()}")
        if (data.type != null) {
            println("Current Data Type ${data.type}")
        }
        println("----------------")
    }
}
