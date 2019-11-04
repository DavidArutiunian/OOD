package command

import document.Document
import document_item.DocumentItem

class DeleteItem(
    private val mDocument: Document,
    private val mPosition: Int
) : AbstractItem<DocumentItem>() {
    private var mLastPos: Int? = null

    override fun doBackup() {}

    override fun doGetBackup(): DocumentItem? {
        return mDocument.getItem(mPosition)
    }

    override fun doExecute() {
        mDocument.INTERNAL_deleteItem(mPosition)
        mLastPos = mPosition
    }

    override fun doUnexecute() {
        val backup = getBackup() ?: return
        mDocument.addItem(backup, mLastPos)
    }
}
