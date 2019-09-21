fun main() {
    val wdIn = WeatherData()
    val wdOut = RxWeatherDataPro()

    val display = Display(wdIn, wdOut)
    val statsDisplay = StatsDisplay(wdOut, wdOut)

    wdIn.registerObserver(display)
    wdIn.registerObserver(statsDisplay)
    wdIn.listenToEvent(display, WeatherInfoType.ANY)
    wdIn.listenToEvent(statsDisplay, WeatherInfoType.ANY)

    wdOut.registerObserver(display)
    wdOut.registerObserver(statsDisplay)
    wdOut.listenToEvent(display, WeatherInfoType.ANY)
    wdOut.listenToEvent(statsDisplay, WeatherInfoType.ANY)

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