package com.david.arutiunian.lab2.output

abstract class OutputStreamImpl : OutputStream {
    override fun writeBlock(source: ByteArray, size: StreamSize) {
        var length = size
        var index = 0

        while (length-- != 0) {
            writeByte(source[index++])
        }
    }
}
