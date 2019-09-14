import io.reactivex.subjects.PublishSubject
import java.util.*

abstract class ObservableImpl<T, E> : Observable<T, E> {
    private val mListeningEvents: MutableMap<Observer<T>, MutableSet<E>> = mutableMapOf()
    private val mQueue: PriorityQueue<Observer<T>> = PriorityQueue(PriorityComparator())
    private val mSubject = PublishSubject.create<Pair<T, Set<E>>>()
    private val mPriorities = mutableMapOf<Observer<T>, Priority>()

    init {
        mSubject.subscribe { onNextCalled(it) }
    }

    private fun onNextCalled(pair: Pair<T, Set<E>>) {
        val data = pair.first
        val events = pair.second

        val observers = arrayListOf<Observer<T>>()
        while (!mQueue.isEmpty()) {
            val top = mQueue.poll()
            observers.add(top)
        }

        observers.forEach { notifyObserverAndReturnToQueue(it, data, events) }
    }

    private fun notifyObserverAndReturnToQueue(observer: Observer<T>, data: T, events: Set<E>) {
        val subscribedTo = mListeningEvents[observer]
        if (subscribedTo.isNullOrEmpty()) {
            observer.update(data, this)
        } else if (events.intersect(subscribedTo).isNotEmpty()) {
            observer.update(data, this)
        }

        mQueue.add(observer)
    }

    override fun registerObserver(observer: Observer<T>, priority: Priority?) {
        mPriorities[observer] = priority ?: 0
        mQueue.offer(observer)
    }

    override fun notifyObservers(events: Set<E>) {
        val data = getChangedData()
        mSubject.onNext(Pair(data, events))
    }

    override fun removeObserver(observer: Observer<T>) {
        mQueue.remove(observer)
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
    }

    protected abstract fun getChangedData(): T

    private inner class PriorityComparator : Comparator<Observer<T>> {
        override fun compare(prev: Observer<T>, curr: Observer<T>): Int {
            val prevPriority = mPriorities[prev] ?: 0
            val currPriority = mPriorities[curr] ?: 0
            return when {
                prevPriority < currPriority -> 1
                prevPriority == currPriority -> 0
                else -> -1
            }
        }
    }
}
