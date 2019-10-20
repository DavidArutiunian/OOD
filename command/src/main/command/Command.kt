package command

interface Command {
    fun execute()

    fun unexecute()

    fun executed(): Boolean
}
