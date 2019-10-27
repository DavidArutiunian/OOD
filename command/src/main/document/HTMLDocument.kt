package document

import command.Command
import command.InsertItem
import document_item.DocumentItem
import document_item.HTMLDocumentItem
import image.HTMLImage
import image.Image
import paragraph.HTMLParagraph
import paragraph.Paragraph
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

class HTMLDocument : Document {
    companion object {
        val IMAGE_DIR = Path.of("images")!!
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
        val image = HTMLImage(path, width, height)
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

    override fun deleteItem(position: Int) {
        mItems.removeAt(position)
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
        val result = StringBuilder()
        result.append("<!doctype html>\n")
        result.append("<html>\n")
        result.append("<head>\n")
        result.append("\t<title>$mTitle</title>\n")
        result.append("</head>\n")
        result.append("<body>\n")
        mItems.forEach { item ->
            when {
                item.getImage() != null -> {
                    val image = item.getImage()!!
                    image.saveTo(path, IMAGE_DIR)
                    result.append("\t<img alt=\"\" src=\"${image.getPath()}\" width=\"${image.getWidth()}\" height=\"${image.getHeight()}\"/>\n")
                }
                item.getParagraph() != null -> {
                    val paragraph = item.getParagraph()!!
                    result.append("\t<p>${escape(paragraph.getText())}</p>\n")
                }
            }
        }
        result.append("</body>\n")
        output.write(result.toString().toByteArray())
        output.flush()
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
        val item = getItem(position)
        if (item.getImage() != null) {
            val image = item.getImage()!!
            image.resize(width, height)
        }
    }

    override fun replaceText(position: Int, text: String) {
        val item = getItem(position)
        if (item.getParagraph() != null) {
            val paragraph = item.getParagraph()!!
            paragraph.setText(text)
        }
    }

    private fun addCommandToHistory(command: Command) {
        command.execute()
        mHistory.removeAll { !it.executed() }
        mHistory.add(command)
    }

    private fun escape(string: String): String {
        val out = StringBuilder(max(16, string.length))
        for (element in string) {
            if (element.toInt() > 127 || element == '"' || element == '<' || element == '>' || element == '&') {
                out.append("&#")
                out.append(element.toInt())
                out.append(';')
            } else {
                out.append(element)
            }
        }
        return out.toString()
    }
}
