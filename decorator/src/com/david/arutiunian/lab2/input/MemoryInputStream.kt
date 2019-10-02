package com.david.arutiunian.lab2.input

import java.io.IOException

class MemoryInputStream(private val stream: ByteArray) : InputStreamImpl() {
    private var position: StreamSize = 0

    override fun isEOF() = position == stream.size

    override fun readByte(): Byte {
        if (position >= stream.size) {
            throw IOException("Failed to load next byte at $position")
        }
        return stream[position++]
    }
}
