package graphics_lib

open class SimpleCanvas: Canvas {
    override fun moveTo(x: Int, y: Int) {
        println("MoveTo ($x, $y)")
    }

    override fun lineTo(x: Int, y: Int) {
        println("LineTo($x, $y)")
    }
}
