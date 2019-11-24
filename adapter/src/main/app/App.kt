package app

fun paintPicture(painter: shape_drawing_lib.CanvasPainter) {
    val triangle = shape_drawing_lib.Triangle(
        shape_drawing_lib.Point(10, 15),
        shape_drawing_lib.Point(100, 200),
        shape_drawing_lib.Point(150, 250),
        0xFFEEBB
    )
    val rectangle = shape_drawing_lib.Rectangle(
        shape_drawing_lib.Point(30, 40),
        18, 24,
        0xAABBCC
    )

    painter.draw(triangle)
    painter.draw(rectangle)
}

fun paintPictureOnCanvas() {
    val canvas = graphics_lib.SimpleCanvas()
    val painter = shape_drawing_lib.CanvasPainter(canvas)
    paintPicture(painter)
}


fun paintPictureOnModernGraphicsRenderer() {
    val renderer = modern_graphics_lib.ModernGraphicsRenderer(System.out)
    renderer.beginDraw()
    renderer.use {
        val canvas = SimpleCanvasAdapter(it)
        val painter = ModernGraphicsRendererAdapter(canvas)
        paintPicture(painter)
    }
}
