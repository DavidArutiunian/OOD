package main.canvas

import main.Point
import java.awt.Color

interface Canvas {
    fun drawLine(from: Point, to: Point, color: Color)

    fun drawEllipse(center: Point, width: Double, height: Double, color: Color)
}
