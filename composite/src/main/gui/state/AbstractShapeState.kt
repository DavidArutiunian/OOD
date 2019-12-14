package gui.state

import model.CanvasShape
import model.StrokeWidth
import java.awt.Color
import java.awt.Point
import java.awt.event.MouseEvent

abstract class AbstractShapeState(
    private val shapes: ArrayList<CanvasShape>,
    private var fillColor: Color,
    private var strokeColor: Color,
    private var strokeWidth: StrokeWidth
) : ShapeState {
    private var shape = createShape()

    private var from: Point = Point(0, 0)
    private var to: Point = Point(0, 0)

    private var added = false

    protected abstract fun createShape(): CanvasShape

    override fun handlePress(event: MouseEvent) {
        shape = createShape()
        shape.setFillStyle(fillColor)
        shape.setStrokeStyle(strokeWidth, strokeColor)
        shape.select()
        from = event.point
    }

    override fun handleDrag(event: MouseEvent) {
        to = event.point
        shape.setFrame(from, to)
        if (!added) {
            shapes.add(shape)
            added = true
        }
    }

    override fun handleRelease(event: MouseEvent) {
        shape.unselect()
        // reset to initial values
        shape = createShape()
        from = Point(0, 0)
        to = Point(0, 0)
        added = false
    }

    override fun fillColorChanged(color: Color) {
        fillColor = color
    }

    override fun strokeColorChanged(color: Color) {
        strokeColor = color
    }
}
