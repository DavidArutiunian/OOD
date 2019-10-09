package main.designer

import main.shape.ShapeFactory
import java.util.*

class CanvasDesigner(private val factory: ShapeFactory) : Designer {
    override fun createDraft(scanner: Scanner): PictureDraft {
        val draft = PictureDraft()
        while (!scanner.hasNext("draw")) {
            val description = scanner.nextLine()
            val shape = factory.createShape(description)
            draft.addShape(shape)
        }
        return draft
    }
}
