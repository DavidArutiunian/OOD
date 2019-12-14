package gui.state

import model.CanvasShape
import model.EllipseShape
import model.StrokeWidth
import java.awt.Color

class EllipseShapeState(
    shapes: ArrayList<CanvasShape>,
    fillColor: Color,
    strokeColor: Color,
    strokeWidth: StrokeWidth
) :
    AbstractShapeState(
        shapes,
        fillColor,
        strokeColor,
        strokeWidth
    ) {
    override fun createShape(): CanvasShape {
        return EllipseShape()
    }
}
