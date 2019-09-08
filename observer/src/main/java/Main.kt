fun main() {
    val comparator = PriorityComparator()

    val wdIn = WeatherData(DuoWeatherChangeDataStrategy(WeatherDataType.IN), comparator)
    val wdOut = WeatherDataPro(ProDuoWeatherChangeDataStrategy(WeatherDataType.OUT), comparator)

    val display = Display()
    wdIn.registerObserver(display)
    wdOut.registerObserver(display)

    val statsDisplay = StatsDisplay()
    wdIn.registerObserver(statsDisplay)
    wdOut.registerObserver(statsDisplay)

    wdIn.setMeasurements(3.0, 0.7, 760.0)
    wdOut.setMeasurements(3.0, 0.7, 760.0, WindInfo(5.0, 20))
    wdIn.setMeasurements(4.0, 0.8, 761.0)
    wdOut.setMeasurements(4.0, 0.8, 761.0, WindInfo(10.0, 180))

    wdIn.removeObserver(statsDisplay)
    wdOut.removeObserver(statsDisplay)

    wdIn.setMeasurements(10.0, 0.8, 761.0)
    wdOut.setMeasurements(10.0, 0.8, 761.0, WindInfo(5.0, 20))
    wdIn.setMeasurements(-10.0, 0.8, 761.0)
    wdOut.setMeasurements(-10.0, 0.8, 761.0, WindInfo(5.0, 180))
}