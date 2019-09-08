interface Observer<T, E> {
    fun update(data: T, events: Set<E>? = null)

    fun getPriority(): Int
}
