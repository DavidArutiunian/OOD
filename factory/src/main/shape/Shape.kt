package main.shape

import main.canvas.Canvas
import java.awt.Color

interface Shape {
    fun draw(canvas: Canvas)
}
