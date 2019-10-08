package main.shape

import main.Point
import java.awt.Color
import java.util.*
import kotlin.collections.ArrayList

class ShapeFactoryImpl : ShapeFactory {
    override fun createShape(description: String): Shape? {
        val scanner = Scanner(description)
        return when (scanner.next()) {
            "rectangle" -> {
                val points = ArrayList<Point>()
                points.add(parsePoint(scanner))
                points.add(parsePoint(scanner))
                points.add(parsePoint(scanner))
                points.add(parsePoint(scanner))
                val color = parseColor(scanner.next())
                if (color != null)
                    Rectangle(points, color)
                else
                    null
            }
            else -> null
        }
    }

    private fun parsePoint(scanner: Scanner): Point {
        return Point(parsePointToken(scanner), parsePointToken(scanner))
    }

    private fun parsePointToken(scanner: Scanner): Double {
        return if (scanner.hasNextDouble()) {
            scanner.nextDouble()
        } else {
            scanner.nextInt().toDouble()
        }
    }

    private fun parseColor(color: String): Color? {
        return when (color) {
            "black" -> Color.BLACK
            "white" -> Color.WHITE
            "red" -> Color.RED
            "green" -> Color.GREEN
            "blue" -> Color.BLUE
            else -> null
        }
    }
}
