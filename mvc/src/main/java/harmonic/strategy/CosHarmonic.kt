package harmonic.strategy

import kotlin.math.cos

class CosHarmonic : HarmonicStrategyImpl() {
    override fun getValueImpl(arg: Double): Double = cos(arg)
}
