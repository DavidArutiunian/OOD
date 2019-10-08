package main.lab2.output

typealias StreamSize = Int

interface OutputStream {
    fun writeByte(byte: Int)

    fun writeBlock(source: IntArray, size: StreamSize)
}
