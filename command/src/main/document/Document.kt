package document

import document_item.DocumentItem
import image.Image
import paragraph.Paragraph
import java.io.OutputStream
import java.nio.file.Path

interface Document {
    fun insertParagraph(text: String, position: Int? = null): Paragraph

    fun insertImage(path: Path, width: Int, height: Int, position: Int? = null): Image

    fun list(stream: OutputStream)

    fun getItemsCount(): Int

    fun getItem(position: Int): DocumentItem

    @Suppress("FunctionName")
    fun INTERNAL_deleteItem(position: Int)

    fun deleteItem(position: Int)

    fun addItem(item: DocumentItem, position: Int?)

    fun getTitle(): String

    fun setTitle(title: String)

    fun canUndo(): Boolean

    fun undo()

    fun canRedo(): Boolean

    fun redo()

    fun save(path: Path)

    fun resizeImage(position: Int, width: Int, height: Int)

    fun replaceText(position: Int, text: String)
}
