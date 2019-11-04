package document_item

import image.Image
import paragraph.Paragraph
import java.io.Closeable

interface DocumentItem : Closeable {
    fun getImage(): Image?

    fun getParagraph(): Paragraph?
}
