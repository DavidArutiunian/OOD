package main.designer

import main.shape.ShapeFactory
import java.util.*

class DesignerImpl(private val factory: ShapeFactory) : Designer {
    override fun createDraft(stream: Scanner): PictureDraft {
        val draft = PictureDraftImpl()
        while (!stream.hasNext("draw")) {
            val description = stream.nextLine()
            val shape = factory.createShape(description)
            if (shape != null) {
                draft.addShape(shape)
            }
        }
        return draft
    }
}
