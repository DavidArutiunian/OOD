interface ProChangeDataStrategy<T> : ChangeDataStrategy<T> {
    fun getChangedData(temperature: Double, humidity: Double, pressure: Double, wind: WindInfo?): T
}