package command

import document.Document
import document_item.DocumentItem

class InsertItem(
    private val mDocument: Document,
    private val mDocumentItem: DocumentItem,
    private val mPosition: Int?
) : AbstractItem<DocumentItem>() {
    private var mLastPos: Int? = null

    override fun doBackup() {
        val backup = getBackup() ?: return
        mDocument.addItem(backup, mLastPos)
    }

    override fun doGetBackup(): DocumentItem? {
        val itemsCount = mDocument.getItemsCount()
        if (itemsCount == 0 || mPosition == null) {
            return null
        }
        return mDocument.getItem(mPosition)
    }

    override fun doExecute() {
        val itemsCount = mDocument.getItemsCount()
        mDocument.addItem(mDocumentItem, mPosition)
        mLastPos = mPosition ?: itemsCount
    }

    override fun doUnexecute() {
        mDocument.INTERNAL_deleteItem(mLastPos!!)
        mLastPos = null
    }

    override fun close() {}
}
