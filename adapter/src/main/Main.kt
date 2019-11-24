fun main() {
    println("Should we use new API (y)?");
    val input = readLine()!!
    if (input == "y" || input == "Y") {
        app.paintPictureOnModernGraphicsRenderer()
    } else {
        app.paintPictureOnCanvas()
    }
}
