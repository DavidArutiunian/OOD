package main.test

import main.lab2.input.FileInputStream
import main.lab2.output.FileOutputStream
import main.lab2.toIntArray
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

@Suppress("FunctionName")
internal class FileOutputStreamTest {
    private val filename = "text.txt"
    private val test = "helloworld"
    private lateinit var file: File

    @BeforeEach
    fun `create test file`() {
        file = File(filename)
        file.createNewFile()
    }

    @AfterEach
    fun `delete test file`() {
        file.delete()
    }

    @Test
    fun `write test bytes by byte`() {
        val expectedByteArray = test.toIntArray()
        val actualByteArray = IntArray(expectedByteArray.size)

        val fileOutputStream = FileOutputStream(filename)

        test.forEach { fileOutputStream.writeByte(it.toInt()) }

        val fileInputStream = FileInputStream(filename)

        fileInputStream.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)

        fileOutputStream.close()
        fileInputStream.close()
    }

    @Test
    fun `write test bytes by block`() {
        val expectedByteArray = test.toIntArray()
        val actualByteArray = IntArray(expectedByteArray.size)

        val fileOutputStream = FileOutputStream(filename)

        fileOutputStream.writeBlock(expectedByteArray, expectedByteArray.size)

        val fileInputStream = FileInputStream(filename)

        fileInputStream.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)

        fileOutputStream.close()
        fileInputStream.close()
    }
}
