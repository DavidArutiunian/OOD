package main.test

import main.lab2.decorators.input.DecompressInputStream
import main.lab2.decorators.output.CompressOutputStream
import main.lab2.input.FileInputStream
import main.lab2.output.FileOutputStream
import main.lab2.toIntArray
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

@Suppress("FunctionName")
internal class DecompressInputStreamTest {
    private val filename = "text.txt"
    private val test = "aaaaabbbbb"
    private lateinit var file: File

    @BeforeEach
    fun `create test file`() {
        file = File(filename)
        file.createNewFile()
        val fileOutputStream = FileOutputStream(filename)
        val compressOutputStream = CompressOutputStream(fileOutputStream)
        val buffer = test.toIntArray()
        compressOutputStream.writeBlock(buffer, buffer.size)
        compressOutputStream.close()
        fileOutputStream.close()
    }

    @AfterEach
    fun `delete test file`() {
        file.delete()
    }

    @Test
    fun `read 10 chars while not EOF`() {
        val fileInputStream = FileInputStream(filename)
        val decompressInputStream = DecompressInputStream(fileInputStream)

        val expectedDigitsLength = 10
        var actualDigitsLength = 0

        while (!decompressInputStream.isEOF()) {
            decompressInputStream.readByte()
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
        val decompressInputStream = DecompressInputStream(fileInputStream)

        var index = 0
        while (!decompressInputStream.isEOF()) {
            expectedByteArray[index++] = decompressInputStream.readByte()
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
        val decompressInputStream = DecompressInputStream(fileInputStream)

        val expectedStreamSize = decompressInputStream.readBlock(expectedByteArray, actualStreamSize)

        assertArrayEquals(actualByteArray, expectedByteArray)
        assertEquals(actualStreamSize, expectedStreamSize)

        fileInputStream.close()
    }
}
