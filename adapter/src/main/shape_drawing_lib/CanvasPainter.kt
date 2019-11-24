package shape_drawing_lib

open class CanvasPainter(private val canvas: graphics_lib.Canvas) {
    open fun draw(drawable: CanvasDrawable) {
        drawable.draw(canvas)
    }
}
