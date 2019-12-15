package model

import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import kotlin.math.max
import kotlin.math.min

abstract class FramableShape : AbstractShape() {
    private var frame = FrameRect(Point(0, 0), Point(0, 0))
    private val frameStrokeWidth = StrokeWidth.ONE
    private val frameStrokeColor = Color(33, 150, 243)
    private val frameFillColor = Color(33, 150, 243, 30)

    private var point: Point? = null

    init {
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                point = e?.point
            }

            override fun mouseReleased(e: MouseEvent?) {
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

    override fun paintFrame(g2d: Graphics2D, rect: Rectangle) {
        // draw frame fill
        g2d.color = frameFillColor
        g2d.stroke = BasicStroke(0f)
        g2d.fill(rect)

        // draw frame stroke
        g2d.color = frameStrokeColor
        g2d.stroke = BasicStroke(frameStrokeWidth.value)
        g2d.draw(rect)
    }

    override fun setFrame(topLeft: Point, bottomRight: Point) {
        frame = FrameRect(topLeft, bottomRight)
    }

    override fun getAbsoluteRect(): Rectangle {
        return transformToAbsoluteRect(frame)
    }

    override fun getInitialRect(): Rectangle {
        return transformToBasicRect(frame)
    }

    private fun transformToBasicRect(frame: FrameRect): Rectangle {
        val x1 = frame.topLeft.x
        val y1 = frame.topLeft.y
        val x2 = frame.bottomRight.x
        val y2 = frame.bottomRight.y
        return Rectangle(Point(x1, y1), Dimension(x2 - x1, y2 - y1))
    }

    private fun transformToAbsoluteRect(frame: FrameRect): Rectangle {
        val x1 = min(frame.topLeft.x, frame.bottomRight.x)
        val y1 = min(frame.topLeft.y, frame.bottomRight.y)
        val x2 = max(frame.topLeft.x, frame.bottomRight.x)
        val y2 = max(frame.topLeft.y, frame.bottomRight.y)
        return Rectangle(Point(x1, y1), Dimension(x2 - x1, y2 - y1))
    }

    private data class FrameRect(val topLeft: Point, val bottomRight: Point)
}
