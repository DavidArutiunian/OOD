package graphics_lib

interface Canvas {
    fun setColor(rgb: Int)

    fun moveTo(x: Int, y: Int)

    fun lineTo(x: Int, y: Int)
}
