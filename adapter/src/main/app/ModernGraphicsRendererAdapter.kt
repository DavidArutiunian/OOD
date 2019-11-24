package app

import shape_drawing_lib.CanvasDrawable

class ModernGraphicsRendererAdapter(private val canvas: SimpleCanvasAdapter) : shape_drawing_lib.CanvasPainter(canvas) {
    override fun draw(drawable: CanvasDrawable) {
        drawable.draw(canvas)
    }
}
