package main.designer

import main.shape.Shape

class PictureDraft {
    private val shapes = ArrayList<Shape>()

    fun getShapeCount() = shapes.size

    fun getShape(index: Int) = shapes[index]

    fun addShape(shape: Shape) {
        shapes.add(shape)
    }
}
