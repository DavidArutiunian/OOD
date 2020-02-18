package harmonic

import graphics.Drawable
import graphics.Graphics
import harmonic.strategy.HarmonicStrategy
import java.awt.Color
import java.awt.Polygon

class AwtHarmonicFunction(
    private val width: Int,
    private val sampling: Int
) : HarmonicFunction, Drawable {
    private var amplitude: Double = 0.0
    private var frequency: Double = 0.0
    private var phase: Double = 0.0

    private var strategy: HarmonicStrategy? = null

    override fun setAmplitude(amplitude: Double) {
        this.amplitude = amplitude
    }

    override fun getAmplitude(): Double = amplitude

    override fun setFrequency(frequency: Double) {
        this.frequency = frequency
    }

    override fun getFrequency(): Double = frequency

    override fun setPhase(phase: Double) {
        this.phase = phase
    }

    override fun getPhase(): Double = phase

    override fun setStrategy(strategy: HarmonicStrategy) {
        this.strategy = strategy
    }

    override fun getStrategy(): HarmonicStrategy? = strategy

    override fun draw(graphics: Graphics) {
        graphics.setColor(Color.BLUE)
        val polyline = Polygon()
        for (step in 0 until width step sampling) {
            val x = step
            val y = (amplitude * (strategy?.getValue(frequency * x + phase) ?: 0.0)).toInt()
            polyline.addPoint(x, y)
        }
        graphics.drawPolyline(polyline.xpoints, polyline.ypoints, polyline.npoints)
    }
}
