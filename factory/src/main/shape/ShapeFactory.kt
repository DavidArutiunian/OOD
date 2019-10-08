package main.shape

interface ShapeFactory {
    fun createShape(description: String): Shape?
}
