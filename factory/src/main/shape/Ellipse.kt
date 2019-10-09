package main.shape

import main.Point
import main.canvas.Canvas
import java.awt.Color

class Ellipse(
    private val mCenter: Point,
    private val mWidth: Double,
    private val mHeight: Double,
    private val mColor: Color
) : Shape {
    override fun draw(canvas: Canvas) {
        canvas.drawEllipse(mCenter, mWidth, mHeight, mColor)
    }
}
