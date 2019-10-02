package com.david.arutiunian.test

import com.david.arutiunian.lab2.input.MemoryInputStream
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@Suppress("FunctionName")
internal class MemoryInputStreamTest {
    private val test = "helloworld"
    private val input = byteArrayOf(*test.toByteArray())

    @Test
    fun `read 10 chars while not EOF`() {
        val stream = MemoryInputStream(input)

        val expectedDigitsLength = 10
        var actualDigitsLength = 0

        while (!stream.isEOF()) {
            stream.readByte()
            actualDigitsLength++
        }

        assertEquals(expectedDigitsLength, actualDigitsLength)
    }

    @Test
    fun `read test data by byte`() {
        val actualByteArray = test.toByteArray()
        val expectedByteArray = ByteArray(actualByteArray.size)

        val stream = MemoryInputStream(input)

        var index = 0
        while (!stream.isEOF()) {
            expectedByteArray[index++] = stream.readByte()
        }

        assertArrayEquals(expectedByteArray, actualByteArray)
    }

    @Test
    fun `read test data by block`() {
        val actualByteArray = test.toByteArray()
        val actualStreamSize = actualByteArray.size
        val expectedByteArray = ByteArray(actualByteArray.size)

        val stream = MemoryInputStream(input)

        val expectedStreamSize = stream.readBlock(expectedByteArray, actualStreamSize)

        assertArrayEquals(actualByteArray, expectedByteArray)
        assertEquals(actualStreamSize, expectedStreamSize)
    }
}
