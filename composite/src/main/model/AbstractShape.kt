package model

import java.awt.*
import java.awt.RenderingHints.KEY_ANTIALIASING
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import java.awt.event.MouseEvent
import javax.swing.JComponent
import javax.swing.JPanel

abstract class AbstractShape : JComponent(), CanvasShape {
    private val observable = ShapeObservable()

    private var fillColor: Color = Color.BLACK
    private var strokeColor: Color = Color.BLACK
    private var strokeWidth = StrokeWidth.ONE

    private var selected = false

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

    protected abstract fun createShape(frame: RectView): Shape

    protected abstract fun getAbsoluteRect(): Rectangle

    protected abstract fun getInitialRect(): Rectangle

    protected abstract fun paintFrame(g2d: Graphics2D, rect: Rectangle)

    override fun paint(g2d: Graphics2D, parent: JPanel) {
        parent.add(this)

        val absoluteRect = getAbsoluteRect()
        val initialRect = getInitialRect()
        val rectView = RectView(absoluteRect, initialRect)
        val shape = createShape(rectView)


        // set size & location of JComponent
        setLocation(absoluteRect.x, absoluteRect.y)
        setSize(absoluteRect.width, absoluteRect.height)

        g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)

        // draw fill
        g2d.color = fillColor
        g2d.fill(shape)

        // draw stroke
        g2d.color = strokeColor
        g2d.stroke = BasicStroke(strokeWidth.value)
        g2d.draw(shape)

        // draw frame if selected
        if (selected) {
            paintFrame(g2d, absoluteRect)
        }
    }

    override fun setStrokeStyle(width: StrokeWidth, color: Color) {
        strokeWidth = width
        strokeColor = color
    }

    override fun getStrokeStyle(): Pair<StrokeWidth?, Color?> {
        return Pair(strokeWidth, strokeColor)
    }

    override fun setFillStyle(color: Color) {
        fillColor = color
    }

    override fun getFillStyle(): Color? {
        return fillColor
    }

    override fun select() {
        selected = true
    }

    override fun unselect() {
        selected = false
    }

    protected data class RectView(val absolute: Rectangle, val initial: Rectangle)
}
