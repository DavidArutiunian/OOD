package shape_drawing_lib

class Rectangle(
    private val leftTop: Point,
    private val width: Int,
    private val height: Int,
    color: Int? = null
) :
    CanvasDrawable {
    private val color = color ?: 0x000000

    override fun draw(canvas: graphics_lib.Canvas) {
        canvas.setColor(color)
        canvas.moveTo(leftTop.x, leftTop.y)
        canvas.lineTo(leftTop.x + width, leftTop.y)
        canvas.lineTo(leftTop.x + width, leftTop.y + height)
        canvas.lineTo(leftTop.x, leftTop.y + height)
        canvas.lineTo(leftTop.x, leftTop.y)
    }
}
