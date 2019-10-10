import main.Point
import main.shape.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.samePropertyValuesAs
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.awt.Color
import java.io.IOException
import kotlin.test.assertEquals

internal class CanvasShapeFactoryTest {
    @Test
    fun `factory parses black rectangle`() {
        val description = "rectangle 10 10 20 10 20 20 10 20 black"

        val expected = Rectangle(
            listOf(
                Point(10.0, 10.0),
                Point(20.0, 10.0),
                Point(20.0, 20.0),
                Point(10.0, 20.0)
            )
            ,
            Color.BLACK
        )

        val factory = CanvasShapeFactory()

        val actual = factory.createShape(description)

        assertThat(actual, samePropertyValuesAs(expected as Shape))
    }

    @Test
    fun `factory parses red ellipse`() {
        val description = "ellipse 100 100 50 50 red"

        val expected = Ellipse(
            Point(100.0, 100.0),
            50.0,
            50.0,
            Color.RED
        )

        val factory = CanvasShapeFactory()

        val actual = factory.createShape(description)

        assertThat(actual, samePropertyValuesAs(expected as Shape))
    }

    @Test
    fun `factory parses green polygon`() {
        val description = "polygon 10 10 20 10 20 20 10 20 50 50 40 40 green"

        val expected = RegularPolygon(
            listOf(
                Point(10.0, 10.0),
                Point(20.0, 10.0),
                Point(20.0, 20.0),
                Point(10.0, 20.0),
                Point(50.0, 50.0),
                Point(40.0, 40.0)
            ),
            Color.GREEN
        )

        val factory = CanvasShapeFactory()

        val actual = factory.createShape(description)

        assertThat(actual, samePropertyValuesAs(expected as Shape))
    }

    @Test
    fun `factory parses blue triangle`() {
        val description = "triangle 10 10 10 50 25 50 blue"

        val expected = Triangle(
            listOf(
                Point(10.0, 10.0),
                Point(10.0, 50.0),
                Point(25.0, 50.0)
            ),
            Color.BLUE
        )

        val factory = CanvasShapeFactory()

        val actual = factory.createShape(description)

        assertThat(actual, samePropertyValuesAs(expected as Shape))
    }

    @Test
    fun `factory throws exception if unknown color`() {
        val description = "triangle 10 10 10 50 25 50 yellow"

        val factory = CanvasShapeFactory()

        val exception = assertThrows<IOException> {
            factory.createShape(description)
        }

        assertEquals("Unsupported color yellow", exception.message)
    }

    @Test
    fun `factory throws exception if unknown shape`() {
        val description = "convex 10 10 10 50 25 50 black"

        val factory = CanvasShapeFactory()

        val exception = assertThrows<IOException> {
            factory.createShape(description)
        }

        assertEquals("Unsupported shape convex", exception.message)
    }
}
