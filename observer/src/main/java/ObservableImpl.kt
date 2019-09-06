import java.util.*

abstract class ObservableImpl<T>(comparator: Comparator<Observer<*>>?) : Observable<T> {
    private val mObservers: PriorityQueue<Observer<T>> = PriorityQueue(comparator)

    override fun registerObserver(observer: Observer<T>) {
        mObservers.add(observer)
    }

    override fun notifyObservers() {
        val data = getChangedData()
        // temporary store observers here
        // while polling them from PriorityQueue
        val tempObserverSet = mutableSetOf<Observer<T>>()
        while (!mObservers.isEmpty()) {
            val top = mObservers.poll()
            top.update(data)
            tempObserverSet.add(top)
        }
        // set observers back to PriorityQueue
        for (observer in tempObserverSet) {
            mObservers.add(observer)
        }
    }

    override fun removeObserver(observer: Observer<T>) {
        mObservers.remove(observer)
    }

    protected abstract fun getChangedData(): T
}
