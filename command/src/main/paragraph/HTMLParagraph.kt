package paragraph

class HTMLParagraph : Paragraph {
    private var mText: String

    constructor(text: String) {
        this.mText = text
    }

    constructor(paragraph: Paragraph) {
        mText = paragraph.getText()
    }

    override fun getText() = mText

    override fun setText(text: String) {
        mText = text
    }
}
