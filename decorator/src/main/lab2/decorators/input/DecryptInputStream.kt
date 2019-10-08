package main.lab2.decorators.input

import main.lab2.input.InputStream
import main.lab2.input.StreamSize
import kotlin.random.Random

class DecryptInputStream(wrappee: InputStream, seed: Int) : InputStreamDecoratorImpl(wrappee) {
    private val length = 256
    private val mDecryptTable = ArrayList<Int>(length)

    init {
        // get encryption table
        val mEncryptTable = ArrayList<Int>(length)
        for (i in 0 until length) {
            mEncryptTable.add(i)
            // fill decrypt table with default values
            mDecryptTable.add(0)

        }
        mEncryptTable.shuffle(Random(seed))

        // decrypt based on encryption table
        for (i in 0..255) {
            mDecryptTable[mEncryptTable[i]] = i
        }
    }

    override fun readByte() = getDecryptedByte(super.readByte())

    override fun readBlock(buffer: IntArray, size: StreamSize): StreamSize {
        val count = super.readBlock(buffer, size)
        for (i in 0 until count) {
            buffer[i] = getDecryptedByte(buffer[i])
        }
        return count
    }

    private fun getDecryptedByte(byte: Int) = mDecryptTable[byte]
}
