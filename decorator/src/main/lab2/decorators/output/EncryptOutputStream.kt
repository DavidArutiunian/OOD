package main.lab2.decorators.output

import main.lab2.output.OutputStream
import main.lab2.output.StreamSize
import kotlin.random.Random

class EncryptOutputStream(wrappee: OutputStream, seed: Int) : OutputStreamDecoratorImpl(wrappee) {
    private val length = 256
    private val mEncryptTable = ArrayList<Int>(length)

    init {
        for (i in 0 until length) {
            mEncryptTable.add(i)
        }
        mEncryptTable.shuffle(Random(seed))
    }

    override fun writeByte(byte: Int) {
        val encrypted = getEncryptedByte(byte)
        super.writeByte(encrypted)
    }

    override fun writeBlock(source: IntArray, size: StreamSize) {
        val copy = intArrayOf(*source)
        for (i in 0 until size) {
            copy[i] = getEncryptedByte(copy[i])
        }
        super.writeBlock(copy, size)
    }

    private fun getEncryptedByte(byte: Int) = mEncryptTable[byte]
}
