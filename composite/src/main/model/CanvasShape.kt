package model

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import javax.swing.JPanel

interface CanvasShape : CanvasShapeObservable {
    fun paint(g2d: Graphics2D, parent: JPanel)

    fun getFillStyle(): Color?

    fun getStrokeStyle(): Pair<StrokeWidth?, Color?>

    fun setStrokeStyle(width: StrokeWidth, color: Color)

    fun setFillStyle(color: Color)

    fun setFrame(topLeft: Point, bottomRight: Point)

    fun getFrame(): FrameRect

    fun select()

    fun unselect()

    fun isSelected(): Boolean

    fun disableInteraction()

    fun enableInteraction()
}
