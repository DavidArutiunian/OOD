package main.lab2.output

import java.io.IOException

class MemoryOutputStream(private val stream: IntArray) : OutputStreamImpl() {
    private var position: StreamSize = 0

    override fun writeByte(byte: Int) {
        if (position >= stream.size) {
            throw IOException("Failed to write next byte at $position")
        }
        stream[position++] = byte
    }
}
