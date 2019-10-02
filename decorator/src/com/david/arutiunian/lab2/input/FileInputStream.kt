package com.david.arutiunian.lab2.input

import java.io.File
import java.io.FileInputStream
import java.io.IOException

class FileInputStream(private val filename: String) : InputStreamImpl() {
    private val stream = FileInputStream(filename)

    init {
        val file = File(filename)

        if (!file.canRead()) {
            throw IOException("Failed to open $filename")
        }
    }

    override fun isEOF() = stream.available() == 0

    override fun readByte(): Byte {
        if (isEOF()) {
            throw IOException("Failed to load next byte from $filename")
        }

        val byte = stream.read()

        return byte.toByte()
    }

    fun close() {
        stream.close()
    }
}
