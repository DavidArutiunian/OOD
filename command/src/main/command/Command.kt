package command

import java.io.Closeable

interface Command : Closeable {
    fun execute()

    fun unexecute()

    fun executed(): Boolean
}
