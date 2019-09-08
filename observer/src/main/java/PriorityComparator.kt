class PriorityComparator : Comparator<Observer<*, *>> {
    override fun compare(prev: Observer<*, *>, curr: Observer<*, *>): Int {
        val prevPriority = prev.getPriority()
        val currPriority = curr.getPriority()
        return when {
            prevPriority < currPriority -> 1
            prevPriority == currPriority -> 0
            else -> -1
        }
    }
}