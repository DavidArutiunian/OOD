package main.canvas

import main.Point
import java.awt.Color

interface Canvas {
    fun setColor(color: Color)

    fun drawLine(from: Point, to: Point)

    fun drawEllipse(center: Point, width: Double, height: Double)
}
