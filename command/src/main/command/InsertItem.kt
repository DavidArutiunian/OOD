package command

import document.Document
import document_item.DocumentItem

class InsertItem(document: Document, item: DocumentItem, position: Int?) : AbstractItem<DocumentItem>() {
    private val mDocument = document
    private val mDocumentItem = item
    private val mPosition = position

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
