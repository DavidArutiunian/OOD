package main.test

import main.lab2.input.FileInputStream
import main.lab2.toIntArray
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
        val fileInputStream = FileInputStream(filename)

        val expectedDigitsLength = 10
        var actualDigitsLength = 0

        while (!fileInputStream.isEOF()) {
            fileInputStream.readByte()
            actualDigitsLength++
        }

        assertEquals(expectedDigitsLength, actualDigitsLength)

        fileInputStream.close()
    }

    @Test
    fun `read test data by byte`() {
        val actualByteArray = test.toIntArray()
        val expectedByteArray = IntArray(actualByteArray.size)

        val fileInputStream = FileInputStream(filename)

        var index = 0
        while (!fileInputStream.isEOF()) {
            expectedByteArray[index++] = fileInputStream.readByte()
        }

        assertArrayEquals(expectedByteArray, actualByteArray)

        fileInputStream.close()
    }

    @Test
    fun `read test data by block`() {
        val actualByteArray = test.toIntArray()
        val actualStreamSize = actualByteArray.size
        val expectedByteArray = IntArray(actualByteArray.size)

        val fileInputStream = FileInputStream(filename)

        val expectedStreamSize = fileInputStream.readBlock(expectedByteArray, actualStreamSize)

        assertArrayEquals(actualByteArray, expectedByteArray)
        assertEquals(actualStreamSize, expectedStreamSize)

        fileInputStream.close()
    }
}
