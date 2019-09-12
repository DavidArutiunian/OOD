data class WindInfo(val speed: Double, val direction: Short)

data class WeatherInfo(
    val temperature: Double,
    val humidity: Double,
    val pressure: Double,
    val wind: WindInfo
)
