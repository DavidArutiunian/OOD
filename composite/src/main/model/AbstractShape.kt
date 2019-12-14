package model

import java.awt.*
import java.awt.RenderingHints.KEY_ANTIALIASING
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.JComponent
import javax.swing.JPanel
import kotlin.math.max
import kotlin.math.min

abstract class AbstractShape : CanvasShape, JComponent() {
    private var fillColor: Color = Color.BLACK
    private var strokeColor: Color = Color.BLACK
    private var strokeWidth = StrokeWidth.ONE

    private var frame = FrameRect(Point(0, 0), Point(0, 0))
    private val frameStrokeWidth = StrokeWidth.ONE
    private val frameStrokeColor = Color(33, 150, 243)
    private val frameFillColor = Color(33, 150, 243, 30)

    private var selected = false
    private var point: Point? = null

    init {
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                select()
                point = e?.point
            }

            override fun mouseReleased(e: MouseEvent?) {
                unselect()
                point = null
            }
        })
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent?) {
                if (e != null && point != null) {
                    frame.topLeft.x += e.x - point!!.x
                    frame.topLeft.y += e.y - point!!.y
                    frame.bottomRight.x += e.x - point!!.x
                    frame.bottomRight.y += e.y - point!!.y
                }
            }
        })
    }

    protected abstract fun createShape(frame: RectView): Shape

    override fun paint(g2d: Graphics2D, parent: JPanel) {
        parent.add(this)

        val absoluteRect = transformToAbsoluteRect(frame)
        val initialRect = transformToRect(frame)
        val rectWithDimensions = RectView(absoluteRect, initialRect)
        val shape = createShape(rectWithDimensions)


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

        if (selected) {
            // draw frame fill
            g2d.color = frameFillColor
            g2d.stroke = BasicStroke(0f)
            g2d.fill(absoluteRect)

            // draw frame stroke
            g2d.color = frameStrokeColor
            g2d.stroke = BasicStroke(frameStrokeWidth.value)
            g2d.draw(absoluteRect)
        }
    }

    override fun containsPoint(point: Point): Boolean {
        val absoluteRect = transformToAbsoluteRect(frame)
        return absoluteRect.contains(point)
    }

    override fun setStrokeStyle(width: StrokeWidth, color: Color) {
        strokeWidth = width
        strokeColor = color
    }

    override fun setFillStyle(color: Color) {
        fillColor = color
    }

    override fun setFrame(topLeft: Point, bottomRight: Point) {
        frame = FrameRect(topLeft, bottomRight)
    }

    override fun select() {
        selected = true
    }

    override fun unselect() {
        selected = false
    }

    private fun transformToAbsoluteRect(frame: FrameRect): Rectangle {
        val x1 = min(frame.topLeft.x, frame.bottomRight.x)
        val y1 = min(frame.topLeft.y, frame.bottomRight.y)
        val x2 = max(frame.topLeft.x, frame.bottomRight.x)
        val y2 = max(frame.topLeft.y, frame.bottomRight.y)
        return Rectangle(Point(x1, y1), Dimension(x2 - x1, y2 - y1))
    }

    private fun transformToRect(frame: FrameRect): Rectangle {
        val x1 = frame.topLeft.x
        val y1 = frame.topLeft.y
        val x2 = frame.bottomRight.x
        val y2 = frame.bottomRight.y
        return Rectangle(Point(x1, y1), Dimension(x2 - x1, y2 - y1))
    }

    private data class FrameRect(val topLeft: Point, val bottomRight: Point)

    protected data class RectView(val absolute: Rectangle, val initial: Rectangle)
}
