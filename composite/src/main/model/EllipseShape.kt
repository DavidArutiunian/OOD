package model

import java.awt.Shape
import java.awt.geom.Ellipse2D

class EllipseShape : FramableShape() {
    override fun createShape(frame: RectView): Shape? {
        val x = frame.absolute.getX()
        val y = frame.absolute.getY()
        val width = frame.absolute.getWidth()
        val height = frame.absolute.getHeight()
        return Ellipse2D.Double(x, y, width, height)
    }
}
