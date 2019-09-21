interface Observer<T> {
    fun update(data: T, currentObservable: BaseObservable<T, *>)
}
