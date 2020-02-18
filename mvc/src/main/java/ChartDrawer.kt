import graphics.AwtGraphics
import graphics.Drawable
import harmonic.AwtHarmonicFunction
import harmonic.strategy.SinHarmonic
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants.EXIT_ON_CLOSE

class ChartDrawerPanel(
    private val drawable: Drawable,
    private val top: Int,
    private val left: Int
) : JPanel() {
    override fun paintComponent(g: Graphics?) {
        val g2d = g as Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        val graphics = AwtGraphics(g2d, top, left)
        drawable.draw(graphics)
    }
}

fun main() {
    val width = 800
    val height = 600
    val amplitude = 25.0
    val frequency = 5.0
    val phase = 0.0
    val sampling = 5
    val harmonic = AwtHarmonicFunction(width, sampling)
    harmonic.setAmplitude(amplitude)
    harmonic.setStrategy(SinHarmonic())
    harmonic.setFrequency(frequency)
    harmonic.setPhase(phase)
    val panel = ChartDrawerPanel(harmonic, height / 2, 0)
    val frame = JFrame()
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.contentPane.add(panel)
    frame.setSize(width, height)
    frame.isVisible = true
    frame.requestFocus()
    frame.setLocationRelativeTo(null)
}
