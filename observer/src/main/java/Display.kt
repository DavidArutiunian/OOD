class Display(priority: Int = 0) : ObserverImpl<WeatherInfo, WeatherInfoType>(priority) {
    override fun update(data: WeatherInfo, events: Set<WeatherInfoType>?) {
        if (events != null) {
            events.forEach {
                when (it) {
                    WeatherInfoType.TEMPERATURE -> println("Current Temp ${data.temperature}")
                    WeatherInfoType.HUMIDITY -> println("Current Hum ${data.humidity}")
                    WeatherInfoType.PRESSURE -> println("Current Pressure ${data.pressure}")
                    WeatherInfoType.WIND -> if (data.wind != null) {
                        println("Current Wind Speed ${data.wind.speed}")
                        println("Current Wind Direction ${data.wind.direction}")
                    }

                }
            }
        } else {
            println("Current Temp ${data.temperature}")
            println("Current Hum ${data.humidity}")
            println("Current Pressure ${data.pressure}")
            if (data.wind != null) {
                println("Current Wind Speed ${data.wind.speed}")
                println("Current Wind Direction ${data.wind.direction}")
            }
        }
        println("Current Priority ${getPriority()}")
        if (data.type != null) {
            println("Current Data Type ${data.type}")
        }
        println("----------------")
    }
}
