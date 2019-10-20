package paragraph

class HTMLParagraph(private var mText: String) : Paragraph {
    override fun getText() = mText

    override fun setText(text: String) {
        mText = text
    }
}
