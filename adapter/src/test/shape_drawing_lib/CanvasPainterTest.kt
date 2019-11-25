package shape_drawing_lib

import app.SimpleCanvasAdapter
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import graphics_lib.SimpleCanvas
import modern_graphics_lib.ModernGraphicsRenderer
import modern_graphics_lib.RGBAColor
import org.junit.jupiter.api.Test

internal class CanvasPainterTest {
    @Test
    fun `painter draws triangle ((10, 15),(100,200),(150,250), 0xFFFFFF) on simple canvas`() {
        val triangle = createTriangle()

        val canvas = mock<SimpleCanvas>()

        doNothing().`when`(canvas).setColor(0xFFFFFF)
        doNothing().`when`(canvas).moveTo(10, 15)
        doNothing().`when`(canvas).lineTo(100, 200)
        doNothing().`when`(canvas).lineTo(150, 250)
        doNothing().`when`(canvas).lineTo(10, 15)

        val painter = CanvasPainter(canvas)

        painter.draw(triangle)

        inOrder(canvas) {
            verify(canvas).setColor(0xFFFFFF)
            verify(canvas).moveTo(10, 15)
            verify(canvas).lineTo(100, 200)
            verify(canvas).lineTo(150, 250)
            verify(canvas).lineTo(10, 15)
        }
    }

    @Test
    fun `painter draws triangle triangle ((10, 15),(100,200),(150,250), 0xFFFFFF) on modern graphics renderer`() {
        val triangle = createTriangle()

        val renderer = mock<ModernGraphicsRenderer>()

        doNothing().`when`(renderer).beginDraw()
        doNothing().`when`(renderer).drawLine(
            modern_graphics_lib.Point(10, 15),
            modern_graphics_lib.Point(100, 200),
            RGBAColor(1.0, 1.0, 1.0, 0.0)
        )
        doNothing().`when`(renderer).drawLine(
            modern_graphics_lib.Point(100, 200),
            modern_graphics_lib.Point(150, 250),
            RGBAColor(1.0, 1.0, 1.0, 0.0)
        )
        doNothing().`when`(renderer).drawLine(
            modern_graphics_lib.Point(150, 250),
            modern_graphics_lib.Point(10, 15),
            RGBAColor(1.0, 1.0, 1.0, 0.0)
        )
        doNothing().`when`(renderer).close()

        val canvas = SimpleCanvasAdapter(renderer)
        val painter = CanvasPainter(canvas)

        renderer.beginDraw()
        renderer.use {
            painter.draw(triangle)
        }

        inOrder(renderer) {
            verify(renderer).beginDraw()
            verify(renderer).drawLine(
                modern_graphics_lib.Point(10, 15),
                modern_graphics_lib.Point(100, 200),
                RGBAColor(1.0, 1.0, 1.0, 0.0)
            )
            verify(renderer).drawLine(
                modern_graphics_lib.Point(100, 200),
                modern_graphics_lib.Point(150, 250),
                RGBAColor(1.0, 1.0, 1.0, 0.0)
            )
            verify(renderer).drawLine(
                modern_graphics_lib.Point(150, 250),
                modern_graphics_lib.Point(10, 15),
                RGBAColor(1.0, 1.0, 1.0, 0.0)
            )
            verify(renderer).close()
        }
    }

    @Test
    fun `painter draws rectangle ((100, 100), 100, 100, 0x000000) on simple canvas`() {
        val rectangle = createRectangle()

        val canvas = mock<SimpleCanvas>()

        doNothing().`when`(canvas).setColor(0x000000)
        doNothing().`when`(canvas).moveTo(100, 100)
        doNothing().`when`(canvas).lineTo(200, 100)
        doNothing().`when`(canvas).lineTo(200, 200)
        doNothing().`when`(canvas).lineTo(100, 200)
        doNothing().`when`(canvas).lineTo(100, 100)

        val painter = CanvasPainter(canvas)

        painter.draw(rectangle)

        inOrder(canvas) {
            verify(canvas).setColor(0x000000)
            verify(canvas).moveTo(100, 100)
            verify(canvas).lineTo(200, 100)
            verify(canvas).lineTo(200, 200)
            verify(canvas).lineTo(100, 200)
            verify(canvas).lineTo(100, 100)
        }
    }

    @Test
    fun `painter draws triangle rectangle ((100, 100), 100, 100, 0x000000) on modern graphics renderer`() {
        val rectangle = createRectangle()

        val renderer = mock<ModernGraphicsRenderer>()

        doNothing().`when`(renderer).beginDraw()
        doNothing().`when`(renderer).drawLine(
            modern_graphics_lib.Point(100, 100),
            modern_graphics_lib.Point(200, 100),
            RGBAColor(0.0, 0.0, 0.0, 0.0)
        )
        doNothing().`when`(renderer).drawLine(
            modern_graphics_lib.Point(200, 100),
            modern_graphics_lib.Point(200, 200),
            RGBAColor(0.0, 0.0, 0.0, 0.0)
        )
        doNothing().`when`(renderer).drawLine(
            modern_graphics_lib.Point(200, 200),
            modern_graphics_lib.Point(100, 200),
            RGBAColor(0.0, 0.0, 0.0, 0.0)
        )
        doNothing().`when`(renderer).drawLine(
            modern_graphics_lib.Point(100, 200),
            modern_graphics_lib.Point(100, 100),
            RGBAColor(0.0, 0.0, 0.0, 0.0)
        )
        doNothing().`when`(renderer).close()

        val canvas = SimpleCanvasAdapter(renderer)
        val painter = CanvasPainter(canvas)

        renderer.beginDraw()
        renderer.use {
            painter.draw(rectangle)
        }

        inOrder(renderer) {
            verify(renderer).beginDraw()
            verify(renderer).drawLine(
                modern_graphics_lib.Point(100, 100),
                modern_graphics_lib.Point(200, 100),
                RGBAColor(0.0, 0.0, 0.0, 0.0)
            )
            verify(renderer).drawLine(
                modern_graphics_lib.Point(200, 100),
                modern_graphics_lib.Point(200, 200),
                RGBAColor(0.0, 0.0, 0.0, 0.0)
            )
            verify(renderer).drawLine(
                modern_graphics_lib.Point(200, 200),
                modern_graphics_lib.Point(100, 200),
                RGBAColor(0.0, 0.0, 0.0, 0.0)
            )
            verify(renderer).drawLine(
                modern_graphics_lib.Point(100, 200),
                modern_graphics_lib.Point(100, 100),
                RGBAColor(0.0, 0.0, 0.0, 0.0)
            )
            verify(renderer).close()
        }
    }

    private fun createTriangle(): Triangle {
        return Triangle(
            Point(10, 15),
            Point(100, 200),
            Point(150, 250),
            0xFFFFFF
        )
    }

    private fun createRectangle(): Rectangle {
        return Rectangle(
            Point(100, 100),
            100,
            100,
            0x000000
        )
    }
}
