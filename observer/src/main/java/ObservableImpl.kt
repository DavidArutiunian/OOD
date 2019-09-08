import java.util.*

abstract class ObservableImpl<T, E>(comparator: Comparator<Observer<*, *>>?) : Observable<T, E> {
    private val mListeningEvents: MutableMap<Observer<T, E>, MutableSet<E>> = mutableMapOf()
    private val mObservers: PriorityQueue<Observer<T, E>> = PriorityQueue(comparator)

    override fun registerObserver(observer: Observer<T, E>) {
        mObservers.add(observer)
    }

    override fun notifyObservers() {
        val data = getChangedData()
        // temporary store observers here
        // while polling them from PriorityQueue
        val tempObserverSet = mutableSetOf<Observer<T, E>>()
        while (!mObservers.isEmpty()) {
            val top = mObservers.poll()
            top.update(data, mListeningEvents[top])
            tempObserverSet.add(top)
        }
        // set observers back to PriorityQueue
        for (observer in tempObserverSet) {
            mObservers.add(observer)
        }
    }

    override fun removeObserver(observer: Observer<T, E>) {
        mObservers.remove(observer)
    }

    override fun listenToEvent(observer: Observer<T, E>, event: E) {
        if (!mListeningEvents.containsKey(observer)) {
            mListeningEvents[observer] = mutableSetOf()
        }
        mListeningEvents[observer]!!.add(event)
    }

    override fun stopListeningEvent(observer: Observer<T, E>, event: E) {
        if (!mListeningEvents.containsKey(observer)) {
            return
        }
        mListeningEvents[observer]!!.remove(event)
    }

    protected abstract fun getChangedData(): T
}
