package com.david.arutiunian.test

import com.david.arutiunian.lab2.input.FileInputStream
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileWriter

@Suppress("FunctionName")
internal class FileInputStreamTest {
    private val filename = "text.txt"
    private val test = "helloworld"
    private lateinit var file: File

    @BeforeEach
    fun `create test file`() {
        file = File(filename)
        file.createNewFile()
        val writer = FileWriter(file)
        writer.append(test)
        writer.close()
    }

    @AfterEach
    fun `delete test file`() {
        file.delete()
    }

    @Test
    fun `read 10 chars while not EOF`() {
        val stream = FileInputStream(filename)

        val expectedDigitsLength = 10
        var actualDigitsLength = 0

        while (!stream.isEOF()) {
            stream.readByte()
            actualDigitsLength++
        }

        assertEquals(expectedDigitsLength, actualDigitsLength)

        stream.close()
    }

    @Test
    fun `read test data by byte`() {
        val actualByteArray = test.toByteArray()
        val expectedByteArray = ByteArray(actualByteArray.size)

        val stream = FileInputStream(filename)

        var index = 0
        while (!stream.isEOF()) {
            expectedByteArray[index++] = stream.readByte()
        }

        assertArrayEquals(expectedByteArray, actualByteArray)

        stream.close()
    }

    @Test
    fun `read test data by block`() {
        val actualByteArray = test.toByteArray()
        val actualStreamSize = actualByteArray.size
        val expectedByteArray = ByteArray(actualByteArray.size)

        val stream = FileInputStream(filename)

        val expectedStreamSize = stream.readBlock(expectedByteArray, actualStreamSize)

        assertArrayEquals(actualByteArray, expectedByteArray)
        assertEquals(actualStreamSize, expectedStreamSize)

        stream.close()
    }
}
