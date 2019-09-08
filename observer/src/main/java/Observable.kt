interface Observable<T, E> {
    fun registerObserver(observer: Observer<T, E>)

    fun listenToEvent(observer: Observer<T, E>, event: E)

    fun stopListeningEvent(observer: Observer<T, E>, event: E)

    fun notifyObservers()

    fun removeObserver(observer: Observer<T, E>)
}