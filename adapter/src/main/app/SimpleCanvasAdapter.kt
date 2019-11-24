package app

class SimpleCanvasAdapter(private val renderer: modern_graphics_lib.ModernGraphicsRenderer) : graphics_lib.SimpleCanvas() {
    private var start: modern_graphics_lib.Point? = null

    override fun moveTo(x: Int, y: Int) {
        start = modern_graphics_lib.Point(x, y)
    }

    override fun lineTo(x: Int, y: Int) {
        val end = modern_graphics_lib.Point(x, y)
        if (start != null) {
            renderer.drawLine(start!!, end)
            start = end
        }
    }
}
