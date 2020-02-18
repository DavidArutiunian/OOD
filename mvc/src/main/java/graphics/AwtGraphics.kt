package graphics

import java.awt.Color

class AwtGraphics(
    private val graphics: java.awt.Graphics2D,
    private val top: Int,
    private val left: Int
) : Graphics {
    override fun setColor(color: Color) {
        graphics.color = color
    }

    override fun drawPolyline(xpoints: IntArray, ypoints: IntArray, size: Int) {
        graphics.translate(left, top)
        graphics.drawPolyline(xpoints, ypoints, size)
    }
}
