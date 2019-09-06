interface Observer<T> {
    fun update(data: T)

    fun getPriority(): Int
}
