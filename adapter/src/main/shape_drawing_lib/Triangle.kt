package shape_drawing_lib

class Triangle(private val p1: Point, private val p2: Point, private val p3: Point, color: Int? = null) :
    CanvasDrawable {
    private val color = color ?: 0x000000

    override fun draw(canvas: graphics_lib.Canvas) {
        canvas.setColor(color)
        canvas.moveTo(p1.x, p1.y)
        canvas.lineTo(p2.x, p2.y)
        canvas.lineTo(p3.x, p3.y)
        canvas.lineTo(p1.x, p1.y)
    }
}
