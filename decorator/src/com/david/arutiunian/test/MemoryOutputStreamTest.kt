package com.david.arutiunian.test

import com.david.arutiunian.lab2.input.MemoryInputStream
import com.david.arutiunian.lab2.output.MemoryOutputStream
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

@Suppress("FunctionName")
internal class MemoryOutputStreamTest {
    private val test = "helloworld"
    private val input = byteArrayOf(*test.toByteArray())

    @Test
    fun `write test bytes by byte`() {
        val expectedByteArray = test.toByteArray()
        val actualByteArray = ByteArray(expectedByteArray.size)

        val out = MemoryOutputStream(input)

        test.forEach { out.writeByte(it.toByte()) }

        val `in` = MemoryInputStream(input)

        `in`.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)
    }

    @Test
    fun `write test bytes by block`() {
        val expectedByteArray = test.toByteArray()
        val actualByteArray = ByteArray(expectedByteArray.size)

        val out = MemoryOutputStream(input)

        out.writeBlock(expectedByteArray, expectedByteArray.size)

        val `in` = MemoryInputStream(input)

        `in`.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)
    }
}
