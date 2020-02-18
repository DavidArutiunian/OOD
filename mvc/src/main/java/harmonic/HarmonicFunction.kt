package harmonic

import harmonic.strategy.HarmonicStrategy

interface HarmonicFunction {
    fun setAmplitude(amplitude: Double)

    fun getAmplitude(): Double

    fun setFrequency(frequency: Double)

    fun getFrequency(): Double

    fun setPhase(phase: Double)

    fun getPhase(): Double

    fun setStrategy(strategy: HarmonicStrategy)

    fun getStrategy(): HarmonicStrategy?
}
