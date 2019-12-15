package model

import java.awt.*
import java.awt.RenderingHints.KEY_ANTIALIASING
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.JComponent
import javax.swing.JPanel

abstract class AbstractShape : JComponent(), CanvasShape {
    private var fillColor: Color = Color.BLACK
    private var strokeColor: Color = Color.BLACK
    private var strokeWidth = StrokeWidth.ONE

    private var interactive = true
    private var selected = false

    private var point: Point? = null

    init {
        initMouseListener()
        initMouseMotionListener()
    }

    private fun initMouseMotionListener() {
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent?) {
                if (point == null || e == null) {
                    return
                }

                val frame = getFrame()
                val diff = FrameRect(
                    Point(e.x - point!!.x, e.y - point!!.y),
                    Point(e.x - point!!.x, e.y - point!!.y)
                )

                doOnMouseDragged(diff)

                if (!interactive) {
                    return
                }
                frame += diff
            }
        })
    }

    private fun initMouseListener() {
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                point = e?.point
            }

            override fun mouseReleased(e: MouseEvent?) {
                point = null
            }
        })
    }

    override fun doOnMousePressed(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        val self = this
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                if (!interactive) {
                    return
                }
                listener(self, e)
            }
        })
    }

    override fun doOnMouseReleased(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        val self = this
        addMouseListener(object : MouseAdapter() {
            override fun mouseReleased(e: MouseEvent?) {
                if (!interactive) {
                    return
                }
                listener(self, e)
            }
        })
    }

    override fun doOnMouseEntered(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        val self = this
        addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent?) {
                listener(self, e)
            }
        })
    }

    override fun doOnMouseExited(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        val self = this
        addMouseListener(object : MouseAdapter() {
            override fun mouseExited(e: MouseEvent?) {
                if (!interactive) {
                    return
                }
                listener(self, e)
            }
        })
    }

    override fun doOnMouseDragged(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit) {
        val self = this
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent?) {
                if (!interactive) {
                    return
                }
                listener(self, e)
            }
        })
    }

    protected abstract fun createShape(frame: RectView): Shape?

    protected abstract fun getAbsoluteRect(): Rectangle

    protected abstract fun getBasicRect(): Rectangle

    protected abstract fun paintFrame(g2d: Graphics2D, rect: Rectangle)

    override fun paint(g2d: Graphics2D, parent: JPanel) {
        parent.add(this)

        val rect = getAbsoluteRect()
        val shape = createShape(RectView(rect, getBasicRect()))

        // set size & location of JComponent
        setLocation(rect.x, rect.y)
        setSize(rect.width, rect.height)

        g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)

        if (shape != null) {
            // draw fill
            g2d.color = fillColor
            g2d.fill(shape)

            // draw stroke
            g2d.color = strokeColor
            g2d.stroke = BasicStroke(strokeWidth.value)
            g2d.draw(shape)
        }

        // draw frame if selected
        if (selected) {
            paintFrame(g2d, rect)
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

    override fun isSelected(): Boolean {
        return selected
    }

    override fun disableInteraction() {
        interactive = false
    }

    override fun enableInteraction() {
        interactive = true
    }

    // contains both absolute & basic rect
    // cause different shapes need different representations coordinates
    // e.g. line need only (start, end) which can be represented by basic rect
    // whereas ellipse needs (top left, width, height) which can be represented by absolute rect
    protected data class RectView(val absolute: Rectangle, val initial: Rectangle)
}
