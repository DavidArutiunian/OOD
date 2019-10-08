package main.lab2.decorators.output

import main.lab2.output.OutputStream
import main.lab2.output.StreamSize
import java.io.Closeable

class CompressOutputStream(wrappee: OutputStream) : OutputStreamDecoratorImpl(wrappee), Closeable {
    override fun close() {
        writeChunk()
    }

    private var mCounter = 0
    private var mByte: Int? = null

    override fun writeByte(byte: Int) {
        when (mByte) {
            null -> setNextByte(byte)
            byte -> mCounter++
            else -> {
                writeChunk()
                setNextByte(byte)
            }
        }
    }

    private fun writeChunk() {
        if (mCounter > 255) {
            var counter = mCounter
            while (counter > 0) {
                super.writeByte(mByte!!)
                val byteCounter =
                    if (counter - (counter % 255) > 0)
                        counter - (counter % 255)
                    else counter
                super.writeByte(byteCounter)
                counter -= 255
            }
        } else {
            super.writeByte(mByte!!)
            super.writeByte(mCounter)
        }
    }

    override fun writeBlock(source: IntArray, size: StreamSize) {
        for (i in 0 until size) {
            writeByte(source[i])
        }
    }

    private fun setNextByte(byte: Int) {
        mByte = byte
        mCounter = 1
    }
}
