package com.david.arutiunian.test

import com.david.arutiunian.lab2.decorators.input.DecryptInputStream
import com.david.arutiunian.lab2.decorators.output.EncryptOutputStream
import com.david.arutiunian.lab2.input.FileInputStream
import com.david.arutiunian.lab2.output.FileOutputStream
import com.david.arutiunian.lab2.toIntArray
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

@Suppress("FunctionName")
internal class EncryptOutputStreamTest {
    private val filename = "text.txt"
    private val test = "helloworld"
    private val seed = 16
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
    fun `write encrypted test bytes by byte`() {
        val expectedByteArray = test.toIntArray()
        val actualByteArray = IntArray(expectedByteArray.size)

        val outputStream = FileOutputStream(filename)
        val encryptOutputStream = EncryptOutputStream(outputStream, seed)

        test.toIntArray().forEach { encryptOutputStream.writeByte(it) }

        val inputStream = FileInputStream(filename)
        val decryptInputStream = DecryptInputStream(inputStream, seed)

        decryptInputStream.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)

        outputStream.close()
        inputStream.close()
    }

    @Test
    fun `write test bytes by block`() {
        val expectedByteArray = test.toIntArray()
        val actualByteArray = IntArray(expectedByteArray.size)

        val fileOutputStream = FileOutputStream(filename)
        val encryptOutputStream = EncryptOutputStream(fileOutputStream, seed)

        encryptOutputStream.writeBlock(expectedByteArray, expectedByteArray.size)

        val fileInputStream = FileInputStream(filename)
        val decryptInputStream = DecryptInputStream(fileInputStream, seed)

        decryptInputStream.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)

        fileOutputStream.close()
        fileInputStream.close()
    }
}
