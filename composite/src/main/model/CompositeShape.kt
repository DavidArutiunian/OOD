package model

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.event.MouseEvent
import javax.swing.JComponent
import javax.swing.JPanel

class CompositeShape(private val shapes: ArrayList<CanvasShape>) : CanvasShape, JComponent() {
    private val observable = ShapeObservable()

    private var fillColor: Color? = null
    private var strokeWidth: StrokeWidth? = null
    private var strokeColor: Color? = null

    init {
        initFillStyle()
        initStrokeStyle()
    }

    private fun initStrokeStyle() {
        val strokeColorStream = shapes
            .stream()
            .map(CanvasShape::getStrokeStyle)
            .distinct()
            .limit(2)
        if (strokeColorStream.count() <= 1) {
            val stroke = strokeColorStream.findFirst().orElse(null)
            strokeWidth = stroke?.first
            strokeColor = stroke?.second
        }
    }

    private fun initFillStyle() {
        val fillColorStream = shapes
            .stream()
            .map(CanvasShape::getFillStyle)
            .distinct()
            .limit(2)
        if (fillColorStream.count() <= 1) {
            fillColor = fillColorStream.findFirst().orElse(null)
        }
    }

    override fun paint(g2d: Graphics2D, parent: JPanel) {
        for (shape in shapes) {
            shape.paint(g2d, parent)
        }
    }

    override fun setStrokeStyle(width: StrokeWidth, color: Color) {
        for (shape in shapes) {
            shape.setStrokeStyle(width, color)
        }
    }

    override fun getStrokeStyle(): Pair<StrokeWidth?, Color?> {
        return Pair(strokeWidth, strokeColor)
    }

    override fun setFillStyle(color: Color) {
        for (shape in shapes) {
            shape.setFillStyle(color)
        }
    }

    override fun getFillStyle(): Color? {
        return fillColor
    }

    override fun setFrame(topLeft: Point, bottomRight: Point) {
        throw NotImplementedError()
    }

    override fun select() {
        for (shape in shapes) {
            shape.select()
        }
    }

    override fun unselect() {
        for (shape in shapes) {
            shape.unselect()
        }
    }

    override fun doOnMousePressed(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        observable.doOnMousePressed(this, this, listener)
    }

    override fun doOnMouseReleased(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        observable.doOnMouseReleased(this, this, listener)
    }

    override fun doOnMouseEntered(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        observable.doOnMouseEntered(this, this, listener)
    }

    override fun doOnMouseExited(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        observable.doOnMouseExited(this, this, listener)
    }

    override fun doOnMouseDragged(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        observable.doOnMouseDragged(this, this, listener)
    }
}
