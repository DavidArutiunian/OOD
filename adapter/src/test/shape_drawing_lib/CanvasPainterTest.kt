package shape_drawing_lib

import app.ModernGraphicsRendererAdapter
import app.SimpleCanvasAdapter
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import graphics_lib.SimpleCanvas
import modern_graphics_lib.ModernGraphicsRenderer
import org.junit.jupiter.api.Test

internal class CanvasPainterTest {
    @Test
    fun `painter draws triangle ((10, 15),(100,200),(150,250) on simple canvas`() {
        val triangle = createTriangle()

        val canvas = mock<SimpleCanvas>()

        doNothing().`when`(canvas).moveTo(10, 15)
        doNothing().`when`(canvas).lineTo(100, 200)
        doNothing().`when`(canvas).lineTo(150, 250)
        doNothing().`when`(canvas).lineTo(10, 15)

        val painter = CanvasPainter(canvas)

        painter.draw(triangle)

        inOrder(canvas) {
            verify(canvas).moveTo(10, 15)
            verify(canvas).lineTo(100, 200)
            verify(canvas).lineTo(150, 250)
            verify(canvas).lineTo(10, 15)
        }
    }

    @Test
    fun `painter draws triangle triangle ((10, 15),(100,200),(150,250) on modern graphics renderer`() {
        val triangle = createTriangle()

        val renderer = mock<ModernGraphicsRenderer>()

        doNothing().`when`(renderer).close()
        doNothing().`when`(renderer).beginDraw()
        doNothing().`when`(renderer).drawLine(modern_graphics_lib.Point(10, 15), modern_graphics_lib.Point(100, 200))
        doNothing().`when`(renderer).drawLine(modern_graphics_lib.Point(100, 200), modern_graphics_lib.Point(150, 250))
        doNothing().`when`(renderer).drawLine(modern_graphics_lib.Point(150, 250), modern_graphics_lib.Point(10, 15))

        val canvas = SimpleCanvasAdapter(renderer)
        val painter = ModernGraphicsRendererAdapter(canvas)

        renderer.beginDraw()
        renderer.use {
            painter.draw(triangle)
        }
    }

    private fun createTriangle(): Triangle {
        return Triangle(
            Point(10, 15),
            Point(100, 200),
            Point(150, 250)
        )
    }
}
