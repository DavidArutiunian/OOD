package document_item

import image.HTMLImage
import image.Image
import paragraph.HTMLParagraph
import paragraph.Paragraph
import java.nio.file.Files

class HTMLDocumentItem : DocumentItem {
    private var mImage: Image? = null
    private var mParagraph: Paragraph? = null

    constructor(image: Image) {
        mImage = image
    }

    constructor(paragraph: Paragraph) {
        mParagraph = paragraph
    }

    constructor(item: DocumentItem) {
        mImage = if (item.getImage() != null) HTMLImage(item.getImage()!!) else null
        mParagraph = if (item.getParagraph() != null) HTMLParagraph(item.getParagraph()!!) else null
    }

    override fun getImage() = mImage

    override fun getParagraph() = mParagraph

    override fun close() {
        if (mImage != null) {
            Files.deleteIfExists(mImage!!.getPath())
        }
    }
}
