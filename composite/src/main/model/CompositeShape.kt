package model

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Shape
import java.util.stream.Stream
import javax.swing.JPanel
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1

class CompositeShape(private val shapes: List<CanvasShape>) : FramableShape() {
    init {
        initFrame()
    }

    private fun initFrame() {
        // get most top left x of children
        val topLeftX = getMinCoordFromShapesBy(FrameRect::topLeft, Point::getX)
        // get most top left y of children
        val topLeftY = getMinCoordFromShapesBy(FrameRect::topLeft, Point::getY)
        // get most bottom right x of children
        val bottomRightX = getMaxCoordFromShapesBy(FrameRect::bottomRight, Point::getX)
        // get most bottom right y of children
        val bottomRightY = getMaxCoordFromShapesBy(FrameRect::bottomRight, Point::getY)
        setFrame(Point(topLeftX, topLeftY), Point(bottomRightX, bottomRightY))
    }

    override fun doOnMouseDragged(diff: FrameRect) {
        for (shape in shapes) {
            val frame = shape.getFrame()
            frame += diff
            // set child frame
            shape.setFrame(frame.topLeft, frame.bottomRight)
            // apply dragging diff
            shape.doOnMouseDragged(diff)
        }
    }

    private fun getMinCoordFromShapesBy(frame: KProperty1<FrameRect, Point>, point: KFunction1<Point, Double>): Int {
        return getCoordFromShapesBy(frame, point)
            .min(Double::compareTo)
            .get()
            .toInt()
    }

    private fun getMaxCoordFromShapesBy(frame: KProperty1<FrameRect, Point>, point: KFunction1<Point, Double>): Int {
        return getCoordFromShapesBy(frame, point)
            .max(Double::compareTo)
            .get()
            .toInt()
    }

    private fun getCoordFromShapesBy(
        frame: KProperty1<FrameRect, Point>,
        point: KFunction1<Point, Double>
    ): Stream<Double> {
        return shapes
            .stream()
            .map(CanvasShape::getFrame)
            .map(frame)
            .map(point)
    }

    override fun createShape(frame: RectView): Shape? {
        return null // draw nothing
    }

    override fun paint(g2d: Graphics2D, parent: JPanel) {
        super.paint(g2d, parent) // paint frame only
        for (shape in shapes) {
            shape.paint(g2d, parent) // paint children
        }
    }

    override fun setStrokeStyle(width: StrokeWidth, color: Color) {
        for (shape in shapes) {
            shape.setStrokeStyle(width, color)
        }
    }

    override fun getStrokeStyle(): Pair<StrokeWidth?, Color?> {
        val strokeColorStream = {
            shapes
                .stream()
                .map(CanvasShape::getStrokeStyle)
                .distinct()
                .limit(2)

        }
        // return width & color only if all members have their same
        // otherwise return pair of nulls
        if (strokeColorStream().count() <= 1) {
            val stroke = strokeColorStream().findFirst().orElse(null)
            return Pair(stroke.first, stroke.second)
        }
        return Pair(null, null)
    }

    override fun setFillStyle(color: Color) {
        for (shape in shapes) {
            shape.setFillStyle(color)
        }
    }

    override fun getFillStyle(): Color? {
        val fillColorStream = {
            shapes
                .stream()
                .map(CanvasShape::getFillStyle)
                .distinct()
                .limit(2)
        }
        // return color only if all members have it's same
        // otherwise return null
        if (fillColorStream().count() <= 1) {
            return fillColorStream().findFirst().orElse(null)
        }
        return null
    }
}
