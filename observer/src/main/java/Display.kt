class Display(priority: Int = 0) : ObserverImpl<WeatherInfo>(priority) {
    override fun update(data: WeatherInfo) {
        println("Current Temp ${data.temperature}")
        println("Current Hum ${data.humidity}")
        println("Current Pressure ${data.pressure}")
        println("Current Priority $mPriority")
        if (data.type != null) {
            println("Current Data Type ${data.type}")
        }
        if (data.wind != null) {
            println("Current Wind Speed ${data.wind.speed}")
            println("Current Wind Direction ${data.wind.direction}")
        }
        println("----------------")
    }
}
