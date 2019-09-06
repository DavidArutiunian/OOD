abstract class ObserverImpl<T>(priority: Int) : Observer<T> {
    protected var mPriority = priority

    override fun getPriority(): Int {
        return mPriority
    }
}