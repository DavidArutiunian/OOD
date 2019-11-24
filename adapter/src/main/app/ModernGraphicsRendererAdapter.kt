package app

class ModernGraphicsRendererAdapter(private val canvas: SimpleCanvasAdapter) : shape_drawing_lib.CanvasPainter(canvas) {
    override fun draw(drawable: shape_drawing_lib.CanvasDrawable) {
        drawable.draw(canvas)
    }
}
