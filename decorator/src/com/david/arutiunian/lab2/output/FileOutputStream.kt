package com.david.arutiunian.lab2.output

import java.io.Closeable
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class FileOutputStream(filename: String) : OutputStreamImpl(), Closeable {
    private val stream = FileOutputStream(filename)

    init {
        if (!Files.isWritable(Path.of(filename))) {
            throw IOException("Failed to open $filename")
        }
    }

    override fun writeByte(byte: Int) {
        stream.write(byte)
    }

    override fun close() {
        stream.close()
    }
}
