abstract class ObserverImpl<T, E>(priority: Int) : Observer<T, E> {
    private var mPriority = priority

    override fun getPriority(): Int {
        return mPriority
    }
}