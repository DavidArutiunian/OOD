package document_item

import image.Image
import paragraph.Paragraph

interface DocumentItem {
    fun getImage(): Image?

    fun getParagraph(): Paragraph?
}
