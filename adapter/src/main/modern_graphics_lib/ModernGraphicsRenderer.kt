package modern_graphics_lib

import java.io.Closeable
import java.io.OutputStream

open class ModernGraphicsRenderer(private val out: OutputStream) : Closeable {
    private var drawing = false

    override fun close() {
        if (drawing) {
            endDraw()
        }
    }

    fun beginDraw() {
        if (drawing) {
            throw RuntimeException("Drawing has already begun")
        }
        out.write("<draw>\n".toByteArray())
        drawing = true
    }

    fun drawLine(start: Point, end: Point, color: RGBAColor = RGBAColor(0.0, 0.0, 0.0, 0.0)) {
        if (!drawing) {
            throw RuntimeException("DrawLine is allowed between beginDraw()/endDraw() only")
        }
        out.write(
            """
                <line fromX="${start.x}" fromY="${start.y}" toX="${end.x}" toY="${end.y}" >
                    <color r="${color.r}" g="${color.g}" b="${color.b}" a="${color.a}" />
                </line>
            """.trimIndent().toByteArray()
        )
        out.write("\n".toByteArray())
    }

    private fun endDraw() {
        if (!drawing) {
            throw RuntimeException("Drawing has not been started")
        }
        out.write("</draw>\n".toByteArray())
        out.flush()
        drawing = false
    }
}
