package main.designer

import main.shape.Shape

interface PictureDraft {
    fun getShapeCount(): Int

    fun getShape(index: Int): Shape
}
