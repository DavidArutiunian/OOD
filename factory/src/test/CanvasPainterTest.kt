import com.nhaarman.mockitokotlin2.*
import main.Point
import main.canvas.Canvas
import main.designer.PictureDraft
import main.painter.CanvasPainter
import main.shape.Ellipse
import main.shape.Triangle
import org.junit.jupiter.api.Test
import java.awt.Color

internal class CanvasPainterTest {
    @Test
    fun `painter draws black ellipse on canvas`() {
        val center = Point(100.0, 100.0)
        val ellipse = Ellipse(center, 50.0, 50.0, Color.BLACK)

        val canvas = mock<Canvas>()

        doNothing().`when`(canvas).drawEllipse(center, 50.0, 50.0, Color.BLACK)

        val painter = CanvasPainter()
        val draft = PictureDraft()

        draft.addShape(ellipse)

        painter.drawPicture(draft, canvas)

        verify(canvas).drawEllipse(center, 50.0, 50.0, Color.BLACK)
    }

    @Test
    fun `painter draws multiple shapes on canvas`() {
        val canvas = mock<Canvas>()

        val center = Point(100.0, 100.0)
        val ellipse = Ellipse(center, 50.0, 50.0, Color.BLACK)

        doNothing().`when`(canvas).drawEllipse(center, 50.0, 50.0, Color.BLACK)

        val vertex1 = Point(10.0, 10.0)
        val vertex2 = Point(10.0, 20.0)
        val vertex3 = Point(15.0, 20.0)
        val points = listOf(vertex1, vertex2, vertex3)
        val triangle = Triangle(points, Color.BLACK)

        doNothing().`when`(canvas).drawLine(vertex1, vertex2, Color.BLACK)
        doNothing().`when`(canvas).drawLine(vertex2, vertex3, Color.BLACK)
        doNothing().`when`(canvas).drawLine(vertex3, vertex1, Color.BLACK)

        val painter = CanvasPainter()
        val draft = PictureDraft()

        draft.addShape(ellipse)
        draft.addShape(triangle)

        painter.drawPicture(draft, canvas)

        verify(canvas).drawEllipse(center, 50.0, 50.0, Color.BLACK)

        verify(canvas).drawLine(vertex1, vertex2, Color.BLACK)
        verify(canvas).drawLine(vertex2, vertex3, Color.BLACK)
        verify(canvas).drawLine(vertex3, vertex1, Color.BLACK)
    }
}
