package com.david.arutiunian.lab2.input

abstract class InputStreamImpl : InputStream {
    override fun readBlock(buffer: ByteArray, size: StreamSize): StreamSize {
        var length = size
        var index = 0

        while (length-- != 0) {
            buffer[index++] = readByte()
        }

        return index
    }
}
