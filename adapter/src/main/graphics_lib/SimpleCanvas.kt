package graphics_lib

open class SimpleCanvas: Canvas {
    override fun setColor(rgb: Int) {
        println("SetColor #${rgb.toString(16).toUpperCase()}")
    }

    override fun moveTo(x: Int, y: Int) {
        println("MoveTo ($x, $y)")
    }

    override fun lineTo(x: Int, y: Int) {
        println("LineTo($x, $y)")
    }
}
