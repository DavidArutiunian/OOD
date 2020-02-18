package harmonic.strategy

import kotlin.math.sin

class SinHarmonic : HarmonicStrategyImpl() {
    override fun getValueImpl(arg: Double): Double = sin(arg)
}
