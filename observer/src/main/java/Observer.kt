interface Observer<T> {
    fun update(data: T, currentObservable: Observable<T, *>)
}
