package model

import java.awt.Shape
import java.awt.geom.Line2D

class LineShape : FramableShape() {
    override fun createShape(frame: RectView): Shape? {
        val x1 = frame.initial.getX()
        val y1 = frame.initial.getY()
        val x2 = x1 + frame.initial.getWidth()
        val y2 = y1 + frame.initial.getHeight()
        return Line2D.Double(x1, y1, x2, y2)
    }
}
