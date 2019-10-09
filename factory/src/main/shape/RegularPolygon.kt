package main.shape

import main.Point
import main.canvas.Canvas
import java.awt.Color

class RegularPolygon(private val mPoints: List<Point>, private val mColor: Color) : Shape {
    override fun draw(canvas: Canvas) {
        val it = mPoints.listIterator()
        var prev = it.next()
        while (it.hasNext()) {
            val curr = it.next()
            canvas.drawLine(prev, curr, mColor)
            prev = curr
        }
    }
}
