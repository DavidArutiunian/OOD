package gui.state

import model.CanvasShape
import model.LineShape
import model.StrokeWidth
import java.awt.Color

class LineShapeState(
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
        return LineShape()
    }
}
