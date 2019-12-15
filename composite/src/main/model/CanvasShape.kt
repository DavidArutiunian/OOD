package model

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import javax.swing.JPanel

interface CanvasShape : CanvasShapeObservable {
    fun paint(g2d: Graphics2D, parent: JPanel)

    fun setStrokeStyle(width: StrokeWidth, color: Color)

    fun setFillStyle(color: Color)

    fun setFrame(topLeft: Point, bottomRight: Point)

    fun select()

    fun unselect()
}
