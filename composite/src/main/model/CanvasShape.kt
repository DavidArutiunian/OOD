package model

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.JPanel

interface CanvasShape {
    fun paint(g2d: Graphics2D, parent: JPanel)

    fun setStrokeStyle(width: StrokeWidth, color: Color)

    fun setFillStyle(color: Color)

    fun setFrame(topLeft: Point, bottomRight: Point)

    fun select()

    fun unselect()

    fun containsPoint(point: Point): Boolean

    fun removeMouseListener(listener: MouseListener)

    fun addMouseListener(listener: MouseListener)

    fun removeMouseMotionListener(listener: MouseMotionListener)

    fun addMouseMotionListener(listener: MouseMotionListener)
}
