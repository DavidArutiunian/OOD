package harmonic.strategy

abstract class HarmonicStrategyImpl : HarmonicStrategy {
    override fun getValue(arg: Double): Double = getValueImpl(arg) * 2 * Math.PI

    protected abstract fun getValueImpl(arg: Double): Double
}
