package main.shape

import main.Point
import main.canvas.Canvas
import java.awt.Color
import java.io.IOException

class Triangle(private val mPoints: List<Point>, private val mColor: Color) : Shape {
    override fun draw(canvas: Canvas) {
        if (mPoints.size < 3) {
            throw IOException("Not enough points for triangle")
        }
        val (vertex1, vertex2, vertex3) = mPoints
        canvas.drawLine(vertex1, vertex2, mColor)
        canvas.drawLine(vertex2, vertex3, mColor)
        canvas.drawLine(vertex3, vertex1, mColor)
    }
}
