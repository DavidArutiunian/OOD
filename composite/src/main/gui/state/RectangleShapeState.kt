package gui.state

import model.CanvasShape
import model.RectangleShape
import model.StrokeWidth
import java.awt.Color

class RectangleShapeState(
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
        return RectangleShape()
    }
}
