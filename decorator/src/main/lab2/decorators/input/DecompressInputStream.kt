package main.lab2.decorators.input

import main.lab2.input.InputStream
import main.lab2.input.StreamSize

class DecompressInputStream(wrappee: InputStream) : InputStreamDecoratorImpl(wrappee) {
    private var mCounter = 0
    private var mByte: Int? = null

    override fun isEOF() = mWrappee.isEOF() && mCounter == 0

    override fun readByte(): Int {
        return if (mCounter != 0) {
            mCounter--
            mByte!!
        } else {
            readByteAndCounter()
            mByte!!
        }
    }

    override fun readBlock(buffer: IntArray, size: StreamSize): StreamSize {
        var bytesRead = 0
        for (i in 0 until size) {
            buffer[i] = readByte()
            bytesRead++
        }
        return bytesRead
    }

    private fun readByteAndCounter() {
        mByte = super.readByte()
        mCounter = super.readByte() - 1
    }
}
