package main.test

import main.lab2.decorators.input.DecompressInputStream
import main.lab2.decorators.output.CompressOutputStream
import main.lab2.input.FileInputStream
import main.lab2.output.FileOutputStream
import main.lab2.toIntArray
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

@Suppress("FunctionName")
internal class CompressOutputStreamTest {
    private val filename = "text.dat"
    private val test = "aaaaabbbbb"
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
        val compressOutputStream = CompressOutputStream(fileOutputStream)

        test.forEach { compressOutputStream.writeByte(it.toInt()) }
        compressOutputStream.close()

        val fileInputStream = FileInputStream(filename)
        val decompressInputStream = DecompressInputStream(fileInputStream)

        decompressInputStream.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)

        fileOutputStream.close()
        fileInputStream.close()
    }

    @Test
    fun `write test bytes by block`() {
        val expectedByteArray = test.toIntArray()
        val actualByteArray = IntArray(expectedByteArray.size)

        val fileOutputStream = FileOutputStream(filename)
        val compressOutputStream = CompressOutputStream(fileOutputStream)

        compressOutputStream.writeBlock(expectedByteArray, expectedByteArray.size)
        compressOutputStream.close()

        val fileInputStream = FileInputStream(filename)
        val decompressInputStream = DecompressInputStream(fileInputStream)

        decompressInputStream.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)

        fileOutputStream.close()
        fileInputStream.close()
    }
}
