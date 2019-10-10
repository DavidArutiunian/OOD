import com.nhaarman.mockitokotlin2.*
import main.Point
import main.designer.CanvasDesigner
import main.shape.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.awt.Color
import java.util.*
import kotlin.test.assertEquals

@Suppress("FunctionName")
internal class CanvasDesignerTest {
    @Test
    fun `designer creates draft with black rectangle`() {
        val captor = argumentCaptor<String>()
        val description = "rectangle 10 10 20 10 20 20 10 20 black"

        val rectangle = Rectangle(
            listOf(
                Point(10.0, 10.0),
                Point(20.0, 10.0),
                Point(20.0, 20.0),
                Point(10.0, 20.0)
            ),
            Color.BLACK
        )

        val scanner = Scanner("$description\ndraw")
        val factory = mock<ShapeFactory> {
            on { createShape(captor.capture()) } doReturn rectangle
        }
        val designer = CanvasDesigner(factory)

        val draft = designer.createDraft(scanner)

        val expectedShapeCount = 1
        assertEquals(expectedShapeCount, draft.getShapeCount())
        val actualRectangle = draft.getShape(0)
        assertThat(actualRectangle, `is`(rectangle as Shape))

        val actualDescription = captor.firstValue
        assertEquals(description, actualDescription)
    }

    @Test
    fun `designer creates draft with red ellipse`() {
        val captor = argumentCaptor<String>()
        val description = "ellipse 100 100 50 50 red"

        val ellipse = Ellipse(
            Point(100.0, 100.0),
            50.0,
            50.0,
            Color.RED
        )

        val scanner = Scanner("$description\ndraw")
        val factory = mock<ShapeFactory> {
            on { createShape(captor.capture()) } doReturn ellipse
        }
        val designer = CanvasDesigner(factory)

        val draft = designer.createDraft(scanner)

        val expectedShapeCount = 1
        assertEquals(expectedShapeCount, draft.getShapeCount())
        val actualEllipse = draft.getShape(0)
        assertThat(actualEllipse, `is`(ellipse as Shape))

        val actualDescription = captor.firstValue
        assertEquals(description, actualDescription)
    }

    @Test
    fun `designer creates draft with green polygon`() {
        val captor = argumentCaptor<String>()
        val description = "polygon 10 10 20 10 20 20 10 20 50 50 40 40 green"

        val polygon = RegularPolygon(
            listOf(
                Point(10.0, 10.0),
                Point(20.0, 10.0),
                Point(20.0, 20.0),
                Point(10.0, 20.0),
                Point(50.0, 50.0),
                Point(40.0, 40.0)
            ),
            Color.YELLOW
        )

        val scanner = Scanner("$description\ndraw")
        val factory = mock<ShapeFactory> {
            on { createShape(captor.capture()) } doReturn polygon
        }
        val designer = CanvasDesigner(factory)

        val draft = designer.createDraft(scanner)

        val expectedShapeCount = 1
        assertEquals(expectedShapeCount, draft.getShapeCount())
        val actualPolygon = draft.getShape(0)
        assertThat(actualPolygon, `is`(polygon as Shape))

        val actualDescription = captor.firstValue
        assertEquals(description, actualDescription)
    }

    @Test
    fun `designer creates draft with blue triangle`() {
        val captor = argumentCaptor<String>()
        val description = "triangle 10 10 10 50 25 50 blue"

        val triangle = Triangle(
            listOf(
                Point(10.0, 10.0),
                Point(10.0, 50.0),
                Point(25.0, 50.0)
            ),
            Color.BLUE
        )

        val scanner = Scanner("$description\ndraw")
        val factory = mock<ShapeFactory> {
            on { createShape(captor.capture()) } doReturn triangle
        }
        val designer = CanvasDesigner(factory)

        val draft = designer.createDraft(scanner)

        val expectedShapeCount = 1
        assertEquals(expectedShapeCount, draft.getShapeCount())
        val actualTriangle = draft.getShape(0)
        assertThat(actualTriangle, `is`(triangle as Shape))

        val actualDescription = captor.firstValue
        assertEquals(description, actualDescription)
    }

    @Test
    fun `designer creates multiple figures`() {
        val captor = argumentCaptor<String>()
        val description = "triangle 10 10 10 50 25 50 blue\n" +
            "ellipse 100 100 50 50 red"

        val triangle = Triangle(
            listOf(
                Point(10.0, 10.0),
                Point(10.0, 50.0),
                Point(25.0, 50.0)
            ),
            Color.BLUE
        )

        val ellipse = Ellipse(
            Point(100.0, 100.0),
            50.0,
            50.0,
            Color.RED
        )

        val scanner = Scanner("$description\ndraw")
        val factory = mock<ShapeFactory> {
            on { createShape(captor.capture()) } doReturnConsecutively listOf(triangle, ellipse)
        }
        val designer = CanvasDesigner(factory)

        val draft = designer.createDraft(scanner)

        val expectedShapeCount = 2
        assertEquals(expectedShapeCount, draft.getShapeCount())
        val actualTriangle = draft.getShape(0)
        assertThat(actualTriangle, `is`(triangle as Shape))
        val actualEllipse = draft.getShape(1)
        assertThat(actualEllipse, `is`(ellipse as Shape))

        val actualDescription = captor.allValues
        assertThat(actualDescription, `is`(description.split("\n")))
    }

    @Test
    fun `designer throws if unknown shape`() {
        val captor = argumentCaptor<String>()
        val description = "convex 10 10 10 50 25 50 blue\n"

        val message = "Unsupported shape convex"

        val scanner = Scanner("$description\ndraw")
        val factory = mock<ShapeFactory> {
            // cannot use IOException cause it's checked :(
            on { createShape(captor.capture()) } doThrow RuntimeException(message)
        }
        val designer = CanvasDesigner(factory)

        val exception = assertThrows<RuntimeException> {
            designer.createDraft(scanner)
        }

        assertEquals(message, exception.message)
    }
}
