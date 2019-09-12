typealias Priority = Int

interface Observable<T, E> {
    fun registerObserver(observer: Observer<T>, priority: Priority? = 0)

    fun listenToEvent(observer: Observer<T>, event: E)

    fun stopListeningEvent(observer: Observer<T>, event: E)

    fun notifyObservers(events: Set<E>)

    fun removeObserver(observer: Observer<T>)
}