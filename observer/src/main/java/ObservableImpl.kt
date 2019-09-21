import io.reactivex.subjects.PublishSubject
import java.util.*

abstract class ObservableImpl<T, E> : Observable<T, E> {
    private val mListeningEvents: MutableMap<Observer<T>, MutableSet<E>> = mutableMapOf()
    private val mTaskQueue: PriorityQueue<Observer<T>> = PriorityQueue(PriorityComparator())
    private val mPublishSubject = PublishSubject.create<Pair<T, Set<E>>>()
    private val mPriorityMap = mutableMapOf<Observer<T>, Priority>()

    init {
        mPublishSubject.subscribe { onNextCalled(it) }
    }

    private fun onNextCalled(pair: Pair<T, Set<E>>) {
        val data = pair.first
        val events = pair.second

        val observers = arrayListOf<Observer<T>>()
        while (!mTaskQueue.isEmpty()) {
            val top = mTaskQueue.poll() ?: continue
            observers.add(top)
        }

        observers.forEach {
            notifyObserver(it, data, events)
            returnToQueue(it)
        }
    }

    private fun returnToQueue(it: Observer<T>) {
        mTaskQueue.add(it)
    }

    protected open fun checkSubscribedEventsImpl(events: Set<E>) = false

    private fun notifyObserver(observer: Observer<T>, data: T, events: Set<E>) {
        val subscribedTo = mListeningEvents[observer]
        if (subscribedTo.isNullOrEmpty()) {
            return
        }
        if (events.intersect(subscribedTo).isNotEmpty() || checkSubscribedEventsImpl(subscribedTo)) {
            observer.update(data, this)
        }
    }

    override fun registerObserver(observer: Observer<T>, priority: Priority?) {
        mPriorityMap[observer] = priority ?: 0
        mTaskQueue.offer(observer)
    }

    override fun notifyObservers(events: Set<E>) {
        val data = getChangedData()
        mPublishSubject.onNext(Pair(data, events))
    }

    override fun removeObserver(observer: Observer<T>) {
        mListeningEvents.remove(observer)
        mTaskQueue.remove(observer)
        mPriorityMap.remove(observer)
    }

    override fun listenToEvent(observer: Observer<T>, event: E) {
        if (!mListeningEvents.containsKey(observer)) {
            mListeningEvents[observer] = mutableSetOf()
        }
        mListeningEvents[observer]!!.add(event)
    }

    override fun stopListeningEvent(observer: Observer<T>, event: E) {
        if (!mListeningEvents.containsKey(observer)) {
            return
        }
        mListeningEvents[observer]!!.remove(event)
        if (mListeningEvents[observer]!!.isEmpty()) {
            mListeningEvents.remove(observer)
        }
    }

    protected abstract fun getChangedData(): T

    private inner class PriorityComparator : Comparator<Observer<T>> {
        override fun compare(prev: Observer<T>, curr: Observer<T>): Int {
            val prevPriority = mPriorityMap[prev] ?: 0
            val currPriority = mPriorityMap[curr] ?: 0
            return when {
                prevPriority < currPriority -> 1
                prevPriority == currPriority -> 0
                else -> -1
            }
        }
    }
}
