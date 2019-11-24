package graphics_lib

class SimpleCanvas: Canvas {
    override fun moveTo(x: Int, y: Int) {
        println("MoveTo ($x, $y)")
    }

    override fun lineTo(x: Int, y: Int) {
        println("LineTo($x, $y)")
    }
}
