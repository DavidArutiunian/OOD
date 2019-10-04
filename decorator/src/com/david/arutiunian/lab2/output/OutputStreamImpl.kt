package com.david.arutiunian.lab2.output

abstract class OutputStreamImpl : OutputStream {
    override fun writeBlock(source: IntArray, size: StreamSize) {
        for (i in 0 until size) {
            writeByte(source[i])
        }
    }
}
