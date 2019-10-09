package main.painter

import main.canvas.Canvas
import main.designer.PictureDraft

class CanvasPainter : Painter {
    override fun drawPicture(draft: PictureDraft, canvas: Canvas) {
        var length = draft.getShapeCount()
        while (length-- > 0) {
            val shape = draft.getShape(length)
            shape.draw(canvas)
        }
    }
}
