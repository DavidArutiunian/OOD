package com.david.arutiunian.lab2.output

typealias StreamSize = Int

interface OutputStream {
    fun writeByte(byte: Byte)

    fun writeBlock(source: ByteArray, size: StreamSize)
}
