package com.david.arutiunian.lab2.output

import java.io.IOException

class MemoryOutputStream(private val stream: ByteArray) : OutputStreamImpl() {
    private var position: StreamSize = 0

    override fun writeByte(byte: Byte) {
        if (position >= stream.size) {
            throw IOException("Failed to write next byte at $position")
        }
        stream[position++] = byte
    }
}
