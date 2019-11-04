package command

import document.Document
import document_item.DocumentItem

class DeleteItem(document: Document, position: Int) : AbstractItem<DocumentItem>() {
    private val mDocument = document
    private val mPosition = position

    private var deleting = false

    override fun doBackup() {}

    override fun doGetBackup(): DocumentItem? {
        return mDocument.getItem(mPosition)
    }

    override fun doExecute() {
        mDocument.INTERNAL_deleteItem(mPosition)
        deleting = true
    }

    override fun doUnexecute() {
        val backup = getBackup() ?: return
        mDocument.addItem(backup, mPosition)
        deleting = false
    }

    override fun close() {
        if (deleting) {
            val item = getBackup()
            item?.close()
        }
    }
}
