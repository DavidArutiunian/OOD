class Display : Observer<WeatherInfo> {
    override fun update(data: WeatherInfo) {
        println("Current Temp ${data.temperature}")
        println("Current Hum ${data.humidity}")
        println("Current Pressure ${data.pressure}")
        println("----------------")
    }
}
