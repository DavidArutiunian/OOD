package main.lab2.input

abstract class InputStreamImpl : InputStream {
    override fun readBlock(buffer: IntArray, size: StreamSize): StreamSize {
        var bytesRead = 0
        for (i in 0 until size) {
            buffer[i] = readByte()
            bytesRead++
        }
        return bytesRead
    }
}
