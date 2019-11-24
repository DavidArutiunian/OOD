package shape_drawing_lib

class CanvasPainter(private val canvas: graphics_lib.Canvas) {
    fun draw(drawable: CanvasDrawable) {
        drawable.draw(canvas)
    }
}
