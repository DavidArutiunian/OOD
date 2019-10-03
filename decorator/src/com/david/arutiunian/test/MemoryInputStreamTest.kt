package com.david.arutiunian.test

import com.david.arutiunian.lab2.input.MemoryInputStream
import com.david.arutiunian.lab2.toIntArray
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@Suppress("FunctionName")
internal class MemoryInputStreamTest {
    private val test = "helloworld"
    private val input = test.toIntArray()

    @Test
    fun `read 10 chars while not EOF`() {
        val memoryInputStream = MemoryInputStream(input)

        val expectedDigitsLength = 10
        var actualDigitsLength = 0

        while (!memoryInputStream.isEOF()) {
            memoryInputStream.readByte()
            actualDigitsLength++
        }

        assertEquals(expectedDigitsLength, actualDigitsLength)
    }

    @Test
    fun `read test data by byte`() {
        val actualByteArray = test.toIntArray()
        val expectedByteArray = IntArray(actualByteArray.size)

        val memoryInputStream = MemoryInputStream(input)

        var index = 0
        while (!memoryInputStream.isEOF()) {
            expectedByteArray[index++] = memoryInputStream.readByte()
        }

        assertArrayEquals(expectedByteArray, actualByteArray)
    }

    @Test
    fun `read test data by block`() {
        val actualByteArray = test.toIntArray()
        val actualStreamSize = actualByteArray.size
        val expectedByteArray = IntArray(actualByteArray.size)

        val memoryInputStream = MemoryInputStream(input)

        val expectedStreamSize = memoryInputStream.readBlock(expectedByteArray, actualStreamSize)

        assertArrayEquals(actualByteArray, expectedByteArray)
        assertEquals(actualStreamSize, expectedStreamSize)
    }
}
