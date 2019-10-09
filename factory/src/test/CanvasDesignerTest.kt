package test.java

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import main.Point
import main.designer.CanvasDesigner
import main.shape.Rectangle
import main.shape.Shape
import main.shape.ShapeFactory
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
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
}
