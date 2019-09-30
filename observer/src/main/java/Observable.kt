import io.reactivex.disposables.Disposable

typealias Priority = Int

interface BaseObservable<T, E> {
    fun listenToEvent(observer: Observer<T>, event: E)

    fun stopListeningEvent(observer: Observer<T>, event: E)

    fun notifyObservers(events: Set<E>)

    fun removeObserver(observer: Observer<T>)
}

interface Observable<T, E> : BaseObservable<T, E> {
    fun registerObserver(observer: Observer<T>)
}

interface PrioritizedObservable<T, E> : BaseObservable<T, E> {
    fun registerObserver(observer: Observer<T>, priority: Priority? = 0)
}
