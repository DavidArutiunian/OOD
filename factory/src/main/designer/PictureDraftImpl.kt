package main.designer

import main.shape.Shape

class PictureDraftImpl : PictureDraft {
    private val shapes = ArrayList<Shape>()

    override fun getShapeCount() = shapes.size

    override fun getShape(index: Int) = shapes[index]

    fun addShape(shape: Shape) {
        shapes.add(shape)
    }
}
