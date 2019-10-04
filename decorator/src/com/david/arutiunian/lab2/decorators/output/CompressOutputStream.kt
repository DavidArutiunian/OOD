package com.david.arutiunian.lab2.decorators.output

import com.david.arutiunian.lab2.output.OutputStream
import com.david.arutiunian.lab2.output.StreamSize
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
        mWrappee.writeByte(mByte!!)
        mWrappee.writeByte(mCounter)
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
