package com.david.arutiunian.test

import com.david.arutiunian.lab2.input.MemoryInputStream
import com.david.arutiunian.lab2.output.MemoryOutputStream
import com.david.arutiunian.lab2.toIntArray
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

@Suppress("FunctionName")
internal class MemoryOutputStreamTest {
    private val test = "helloworld"
    private val input = test.toIntArray()

    @Test
    fun `write test bytes by byte`() {
        val expectedByteArray = test.toIntArray()
        val actualByteArray = IntArray(expectedByteArray.size)

        val memoryOutputStream = MemoryOutputStream(input)

        test.forEach { memoryOutputStream.writeByte(it.toInt()) }

        val memoryInputStream = MemoryInputStream(input)

        memoryInputStream.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)
    }

    @Test
    fun `write test bytes by block`() {
        val expectedByteArray = test.toIntArray()
        val actualByteArray = IntArray(expectedByteArray.size)

        val memoryOutputStream = MemoryOutputStream(input)

        memoryOutputStream.writeBlock(expectedByteArray, expectedByteArray.size)

        val memoryInputStream = MemoryInputStream(input)

        memoryInputStream.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)
    }
}
