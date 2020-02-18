package graphics

import java.awt.Color

interface Graphics {
    fun setColor(color: Color)

    fun drawPolyline(xpoints: IntArray, ypoints: IntArray, size: Int)
}
