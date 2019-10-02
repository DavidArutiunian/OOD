package com.david.arutiunian.lab2.input

typealias StreamSize = Int

interface InputStream {
    fun isEOF(): Boolean

    fun readByte(): Byte

    fun readBlock(buffer: ByteArray, size: StreamSize): StreamSize
}
