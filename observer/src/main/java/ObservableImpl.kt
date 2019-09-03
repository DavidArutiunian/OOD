abstract class ObservableImpl<T> : Observable<T> {
    private val mObservers: MutableSet<Observer<T>> = mutableSetOf()

    override fun registerObserver(observer: Observer<T>) {
        mObservers.add(observer)
    }

    override fun notifyObservers() {
        val data = getChangedData()
        for (observer in mObservers) {
            observer.update(data)
        }
    }

    override fun removeObserver(observer: Observer<T>) {
        mObservers.remove(observer)
    }

    protected abstract fun getChangedData(): T
}
