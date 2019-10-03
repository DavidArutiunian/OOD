package com.david.arutiunian.lab2.input

typealias StreamSize = Int

interface InputStream {
    fun isEOF(): Boolean

    fun readByte(): Int

    fun readBlock(buffer: IntArray, size: StreamSize): StreamSize
}
