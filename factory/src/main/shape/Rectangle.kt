package main.shape

import main.Point
import main.canvas.Canvas
import java.awt.Color
import java.io.IOException

class Rectangle(private val mPoints: List<Point>, private val mColor: Color) : Shape {
    override fun draw(canvas: Canvas) {
        if (mPoints.size < 4) {
            throw IOException("Not enough points for rectangle")
        }
        val (topLeft, topRight, bottomRight, bottomLeft) = mPoints
        canvas.drawLine(topLeft, topRight, mColor)
        canvas.drawLine(topRight, bottomRight, mColor)
        canvas.drawLine(bottomRight, bottomLeft, mColor)
        canvas.drawLine(bottomLeft, topLeft, mColor)
    }
}
