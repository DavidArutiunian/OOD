package main.test

import main.lab2.decorators.input.DecryptInputStream
import main.lab2.decorators.output.EncryptOutputStream
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
internal class DecryptInputStreamTest {
    private val seed = 16
    private val filename = "text.txt"
    private val test = "helloworld"
    private lateinit var file: File

    @BeforeEach
    fun `create test file`() {
        file = File(filename)
        file.createNewFile()
        val fileOutputStream = FileOutputStream(filename)
        val encryptOutputStream = EncryptOutputStream(fileOutputStream, seed)
        val buffer = test.toIntArray()
        encryptOutputStream.writeBlock(buffer, buffer.size)
        fileOutputStream.close()
    }

    @AfterEach
    fun `delete test file`() {
        file.delete()
    }

    @Test
    fun `read 10 chars while not EOF`() {
        val fileInputStream = FileInputStream(filename)
        val decryptInputStream = DecryptInputStream(fileInputStream, seed)

        val expectedDigitsLength = 10
        var actualDigitsLength = 0

        while (!decryptInputStream.isEOF()) {
            decryptInputStream.readByte()
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
        val decryptInputStream = DecryptInputStream(fileInputStream, seed)

        var index = 0
        while (!decryptInputStream.isEOF()) {
            expectedByteArray[index++] = decryptInputStream.readByte()
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
        val decryptInputStream = DecryptInputStream(fileInputStream, seed)

        val expectedStreamSize = decryptInputStream.readBlock(expectedByteArray, actualStreamSize)

        assertArrayEquals(actualByteArray, expectedByteArray)
        assertEquals(actualStreamSize, expectedStreamSize)

        fileInputStream.close()
    }
}
