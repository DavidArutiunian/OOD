package model

import java.awt.*
import kotlin.math.max
import kotlin.math.min

abstract class FramableShape : AbstractShape() {
    private var frame = FrameRect(Point(0, 0), Point(0, 0))

    private val frameStrokeWidth = StrokeWidth.ONE
    private val frameStrokeColor = Color(33, 150, 243)
    private val frameFillColor = Color(33, 150, 243, 30)

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

    override fun getFrame(): FrameRect {
        return frame
    }

    // correctly calculated rect
    // has always top left at real top left
    // and bottom right at real bottom right
    override fun getAbsoluteRect(): Rectangle {
        return transformToAbsoluteRect(frame)
    }

    // can contain negative x/y/width/height
    // represents where user clicked and where dragged
    override fun getBasicRect(): Rectangle {
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

    override fun doOnMouseDragged(diff: FrameRect) {}
}
