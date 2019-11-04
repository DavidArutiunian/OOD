package document

import command.Command
import command.DeleteItem
import command.InsertItem
import deleteDirectory
import document_item.DocumentItem
import document_item.HTMLDocumentItem
import escape
import image.HTMLImage
import image.Image
import paragraph.HTMLParagraph
import paragraph.Paragraph
import java.io.Closeable
import java.io.IOException
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.collections.ArrayList

class HTMLDocument : Document, Closeable {
    companion object {
        val IMAGE_DIR = Path.of("images")!!
        val TEMP_DIR = Path.of(".tmp")!!
        const val MAX_STACK_SIZE = 10
    }

    init {
        if (Files.notExists(TEMP_DIR)) {
            Files.createDirectory(TEMP_DIR)
        }
    }

    override fun close() {
        deleteDirectory(TEMP_DIR.toFile())
    }

    private var mTitle = ""
    private val mItems = ArrayList<DocumentItem>()
    private val mHistory = Stack<Command>()

    override fun insertParagraph(text: String, position: Int?): Paragraph {
        val paragraph = HTMLParagraph(text)
        val item = HTMLDocumentItem(paragraph)
        val command = InsertItem(this, item, position)
        addCommandToHistory(command)
        return paragraph
    }

    override fun insertImage(path: Path, width: Int, height: Int, position: Int?): Image {
        val tempImagePath = copyToTempImageDir(path)
        val image = HTMLImage(tempImagePath, width, height)
        val item = HTMLDocumentItem(image)
        val command = InsertItem(this, item, position)
        addCommandToHistory(command)
        return image
    }

    override fun getItemsCount() = mItems.size

    override fun getItem(position: Int) = mItems[position]

    private fun setItem(position: Int, item: DocumentItem) {
        mItems[position] = item
    }

    override fun INTERNAL_deleteItem(position: Int) {
        mItems.removeAt(position)
    }

    override fun deleteItem(position: Int) {
        val command = DeleteItem(this, position)
        addCommandToHistory(command)
    }

    override fun getTitle() = mTitle

    override fun setTitle(title: String) {
        mTitle = title
    }

    override fun canUndo(): Boolean {
        return mHistory.any { it.executed() }
    }

    override fun undo() {
        mHistory.findLast { it.executed() }?.unexecute()
    }

    override fun canRedo(): Boolean {
        return mHistory.any { !it.executed() }
    }

    override fun redo() {
        mHistory.find { !it.executed() }?.execute()
    }

    override fun save(path: Path) {
        val output = Files.newOutputStream(path)
        output.use {
            val result = StringBuilder()
            result.append(
                """
                <!doctype html>
                <html>
                <head>
                <title>$mTitle</title>
                </head>
                <body>
                """.trimIndent()
            )
            mItems.forEach { item ->
                when {
                    item.getImage() != null -> {
                        val image = item.getImage()!!
                        image.saveTo(path, IMAGE_DIR)
                        result.append(
                            """
                            <img 
                              alt="${image.getPath().fileName}" 
                              src="${image.getPath()}" 
                              width="${image.getWidth()}" 
                              height="${image.getHeight()}"
                            />
                            """.trimIndent()
                        )
                    }
                    item.getParagraph() != null -> {
                        val paragraph = item.getParagraph()!!
                        result.append(
                            """
                            <p>${paragraph.getText().escape()}</p>
                            """.trimIndent()
                        )
                    }
                }
            }
            result.append(
                """
                </body>
                </html>
                """.trimIndent()
            )
            output.write(result.toString().toByteArray())
        }
    }

    override fun addItem(item: DocumentItem, position: Int?) {
        when {
            position == null -> mItems.add(item)
            position < mItems.size -> setItem(position, item)
            else -> mItems.add(position, item)
        }
    }

    override fun list(stream: OutputStream) {
        val result = StringBuilder()
        result.append("Title: $mTitle\n")
        mItems.forEachIndexed { index, item ->
            when {
                item.getImage() != null -> {
                    val image = item.getImage()!!
                    val path = image.getPath().toAbsolutePath().normalize()
                    result.append("$index. Image: ${image.getWidth()} ${image.getHeight()} $path\n")
                }
                item.getParagraph() != null -> {
                    val paragraph = item.getParagraph()!!
                    result.append("$index: Paragraph: ${paragraph.getText()}\n")
                }
            }
        }
        stream.write(result.toString().toByteArray())
    }

    override fun resizeImage(position: Int, width: Int, height: Int) {
        val item = HTMLDocumentItem(getItem(position))
        if (item.getImage() != null) {
            val image = item.getImage()!!
            image.resize(width, height)
            val command = InsertItem(this, item, position, true)
            addCommandToHistory(command)
        }
    }

    override fun replaceText(position: Int, text: String) {
        val item = HTMLDocumentItem(getItem(position))
        if (item.getParagraph() != null) {
            val paragraph = item.getParagraph()!!
            paragraph.setText(text)
            val command = InsertItem(this, item, position, true)
            addCommandToHistory(command)
        }
    }

    private fun addCommandToHistory(command: Command) {
        if (mHistory.size == MAX_STACK_SIZE) {
            val first = mHistory.first()
            first.close()
            mHistory.remove(first)
        }
        command.execute()
        mHistory.takeWhile { !it.executed() }.forEach { it.close() }
        mHistory.removeAll { !it.executed() }
        mHistory.add(command)
    }

    private fun copyToTempImageDir(path: Path): Path {
        if (Files.notExists(path)) {
            throw IOException("File $path does not exist")
        }
        val tempFilePath = Path.of("$TEMP_DIR/${path.fileName}")
        Files.copy(path, tempFilePath)
        return tempFilePath
    }
}
