fun main() {
    val comparator = PriorityComparator()

    val wdIn = WeatherDataPro(ProDuoWeatherChangeDataStrategy(WeatherDataType.IN), comparator)
    val wdOut = WeatherData(DuoWeatherChangeDataStrategy(WeatherDataType.OUT), comparator)

    val display = Display()
    wdIn.registerObserver(display)
    wdOut.registerObserver(display)

    val statsDisplay = StatsDisplay()
    wdIn.registerObserver(statsDisplay)
    wdOut.registerObserver(statsDisplay)

    wdIn.setMeasurements(3.0, 0.7, 760.0, WindInfo(5.0, 20))
    wdOut.setMeasurements(3.0, 0.7, 760.0)
    wdIn.setMeasurements(4.0, 0.8, 761.0, WindInfo(10.0, 180))
    wdOut.setMeasurements(4.0, 0.8, 761.0)

    wdIn.removeObserver(statsDisplay)
    wdOut.removeObserver(statsDisplay)

    wdIn.setMeasurements(10.0, 0.8, 761.0, WindInfo(5.0, 20))
    wdOut.setMeasurements(10.0, 0.8, 761.0)
    wdIn.setMeasurements(-10.0, 0.8, 761.0, WindInfo(5.0, 180))
    wdOut.setMeasurements(-10.0, 0.8, 761.0)
}