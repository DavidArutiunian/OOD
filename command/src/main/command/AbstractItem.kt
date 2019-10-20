package command

abstract class AbstractItem<T> : Command {
    private var mExecuted = false
    private var mBackup: T? = null

    override fun execute() {
        mBackup = doGetBackup()
        doExecute()
        setExecuted()
    }

    override fun unexecute() {
        doUnexecute()
        doBackup()
        setUnexecuted()
    }

    private fun setUnexecuted() {
        mExecuted = false
    }

    private fun setExecuted() {
        mExecuted = true
    }

    override fun executed() = mExecuted

    protected fun getBackup() = mBackup

    protected abstract fun doBackup()

    protected abstract fun doGetBackup(): T?

    protected abstract fun doExecute()

    protected abstract fun doUnexecute()
}
