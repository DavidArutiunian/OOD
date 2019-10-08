package main.shape

import main.Point
import main.canvas.Canvas
import java.awt.Color

class Rectangle(private val mPoints: List<Point>, private val mColor: Color) : Shape {
    override fun draw(canvas: Canvas) {
        assert(mPoints.size == 4)
        val (topLeft, topRight, bottomRight, bottomLeft) = mPoints
        canvas.drawLine(topLeft, topRight)
        canvas.drawLine(topRight, bottomRight)
        canvas.drawLine(bottomRight, bottomLeft)
        canvas.drawLine(bottomLeft, topLeft)
    }

    override fun getColor() = mColor
}
