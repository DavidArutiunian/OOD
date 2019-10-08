package main.lab2.decorators.output

import main.lab2.output.OutputStream
import main.lab2.output.StreamSize

abstract class OutputStreamDecoratorImpl(protected val mWrappee: OutputStream) : OutputStream {
    override fun writeByte(byte: Int) = mWrappee.writeByte(byte)

    override fun writeBlock(source: IntArray, size: StreamSize) = mWrappee.writeBlock(source, size)
}
