package main.lab2.input

import java.io.Closeable
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class FileInputStream(filename: String) : InputStreamImpl(), Closeable {
    private val stream = FileInputStream(filename)

    init {
        if (!Files.isReadable(Path.of(filename))) {
            throw IOException("Failed to open $filename")
        }
    }

    override fun isEOF() = stream.available() == 0

    override fun readByte(): Int {
        if (isEOF()) {
            throw IOException("Failed to load next byte")
        }

        return stream.read()
    }

    override fun close() {
        stream.close()
    }
}
