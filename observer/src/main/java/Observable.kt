interface Observable<T> {
    fun registerObserver(observer: Observer<T>)

    fun notifyObservers()

    fun removeObserver(observer: Observer<T>)
}