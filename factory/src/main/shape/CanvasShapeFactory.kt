package main.shape

import main.Point
import java.awt.Color
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class CanvasShapeFactory : ShapeFactory {
    override fun createShape(description: String): Shape {
        val scanner = Scanner(description)
        return when (val shape = scanner.next()) {
            "rectangle" -> {
                val points = ArrayList<Point>()
                points.add(parsePoint(scanner))
                points.add(parsePoint(scanner))
                points.add(parsePoint(scanner))
                points.add(parsePoint(scanner))
                val color = parseColor(scanner)
                Rectangle(points, color)
            }
            "triangle" -> {
                val points = ArrayList<Point>()
                points.add(parsePoint(scanner))
                points.add(parsePoint(scanner))
                points.add(parsePoint(scanner))
                val color = parseColor(scanner)
                Triangle(points, color)
            }
            "ellipse" -> {
                val center = parsePoint(scanner)
                val width = parseNumber(scanner)
                val height = parseNumber(scanner)
                val color = parseColor(scanner)
                Ellipse(center, width, height, color)
            }
            "polygon" -> {
                val points = ArrayList<Point>()
                while (hasNextNumber(scanner)) {
                    val point = parsePoint(scanner)
                    points.add(point)
                }
                val color = parseColor(scanner)
                RegularPolygon(points, color)

            }
            else -> throw IOException("Unsupported shape $shape")
        }
    }

    private fun hasNextNumber(scanner: Scanner): Boolean {
        return scanner.hasNextDouble() || scanner.hasNextInt()
    }

    private fun parsePoint(scanner: Scanner): Point {
        return Point(parseNumber(scanner), parseNumber(scanner))
    }

    private fun parseNumber(scanner: Scanner): Double {
        return if (scanner.hasNextDouble()) {
            scanner.nextDouble()
        } else {
            scanner.nextInt().toDouble()
        }
    }

    private fun parseColor(scanner: Scanner): Color {
        return when (val color = scanner.next()) {
            "black" -> Color.BLACK
            "white" -> Color.WHITE
            "red" -> Color.RED
            "green" -> Color.GREEN
            "blue" -> Color.BLUE
            else -> throw IOException("Unsupported color $color")
        }
    }
}
