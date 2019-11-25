package app.class_adapter

class SimpleCanvasAdapter : modern_graphics_lib.ModernGraphicsRenderer(System.out), graphics_lib.Canvas {
    private lateinit var start: modern_graphics_lib.Point
    private lateinit var color: modern_graphics_lib.RGBAColor

    override fun setColor(rgb: Int) {
        val r = (rgb and 0xFF0000 shr 16).toDouble() / 0xFF
        val g = (rgb and 0xFF00 shr 8).toDouble() / 0xFF
        val b = (rgb and 0xFF).toDouble() / 0xFF
        color = modern_graphics_lib.RGBAColor(r, g, b, 0.0)
    }


    override fun moveTo(x: Int, y: Int) {
        start = modern_graphics_lib.Point(x, y)
    }

    override fun lineTo(x: Int, y: Int) {
        val end = modern_graphics_lib.Point(x, y)
        drawLine(start, end, color)
        start = end
    }
}
