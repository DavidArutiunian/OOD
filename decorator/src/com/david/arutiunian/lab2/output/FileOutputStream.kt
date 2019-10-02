package com.david.arutiunian.lab2.output

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileOutputStream(filename: String) : OutputStreamImpl() {
    private val stream = FileOutputStream(filename)

    init {
        val file = File(filename)

        if (!file.canWrite()) {
            throw IOException("Failed to open $filename")
        }
    }

    override fun writeByte(byte: Byte) {
        stream.write(byte.toInt())
    }

    fun close() {
        stream.close()
    }
}
