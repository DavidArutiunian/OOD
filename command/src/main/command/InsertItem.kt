package command

import document.Document
import document_item.DocumentItem

class InsertItem(document: Document, item: DocumentItem, position: Int?, replacing: Boolean = false) :
    AbstractItem<DocumentItem>() {
    private val mDocument = document
    private val mDocumentItem = item
    private val mPosition = position
    private val mReplacing = replacing

    private var mLastPos: Int? = null

    override fun doBackup() {
        val backup = getBackup() ?: return
        if (mReplacing) {
            val (items, count) = getItemsDownToLastPos()
            mDocument.addItem(backup, mLastPos)
            fillWithItems(count, items)
        } else {
            mDocument.addItem(backup, mLastPos)
        }
    }

    private fun fillWithItems(count: Int, items: ArrayList<DocumentItem>) {
        for (i in mLastPos!! until count) {
            mDocument.addItem(items[i], null)
        }
    }

    private fun getItemsDownToLastPos(): Pair<ArrayList<DocumentItem>, Int> {
        val items = arrayListOf<DocumentItem>()
        val count = mDocument.getItemsCount()
        for (i in count - 1 downTo mLastPos!!) {
            items.add(mDocument.getItem(i))
        }
        return Pair(items, count)
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
        if (!mReplacing) {
            mLastPos = null
        }
    }

    override fun close() {}
}
