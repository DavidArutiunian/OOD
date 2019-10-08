package main.painter

import main.canvas.Canvas
import main.designer.PictureDraft

interface Painter {
    fun drawPicture(draft: PictureDraft, canvas: Canvas)
}
