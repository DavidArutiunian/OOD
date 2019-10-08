package main.lab2.decorators.input

import main.lab2.input.InputStream
import main.lab2.input.StreamSize

abstract class InputStreamDecoratorImpl(protected val mWrappee: InputStream) : InputStream {
    override fun isEOF() = mWrappee.isEOF()

    override fun readByte() = mWrappee.readByte()

    override fun readBlock(buffer: IntArray, size: StreamSize) = mWrappee.readBlock(buffer, size)
}
