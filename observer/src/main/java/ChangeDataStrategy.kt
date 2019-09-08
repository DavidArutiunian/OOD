interface ChangeDataStrategy<T> {
    fun getChangedData(temperature: Double, humidity: Double, pressure: Double): T
}