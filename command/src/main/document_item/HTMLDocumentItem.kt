package document_item

import image.Image
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

    override fun getImage() = mImage

    override fun getParagraph() = mParagraph

    override fun close() {
        if (mImage != null) {
            Files.deleteIfExists(mImage!!.getPath())
        }
    }
}
