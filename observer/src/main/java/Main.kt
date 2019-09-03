fun main() {
    val wd = WeatherData()

    val display = Display()
    wd.registerObserver(display)

    val statsDisplay = StatsDisplay()
    wd.registerObserver(statsDisplay)

    wd.setMeasurements(3.0, 0.7, 760.0)
    wd.setMeasurements(4.0, 0.8, 761.0)

    wd.removeObserver(statsDisplay)

    wd.setMeasurements(10.0, 0.8, 761.0)
    wd.setMeasurements(-10.0, 0.8, 761.0)
}