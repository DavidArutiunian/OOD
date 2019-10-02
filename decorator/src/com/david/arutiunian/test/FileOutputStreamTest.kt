package com.david.arutiunian.test

import com.david.arutiunian.lab2.input.FileInputStream
import com.david.arutiunian.lab2.output.FileOutputStream
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
        val expectedByteArray = test.toByteArray()
        val actualByteArray = ByteArray(expectedByteArray.size)

        val out = FileOutputStream(filename)

        test.forEach { out.writeByte(it.toByte()) }

        val `in` = FileInputStream(filename)

        `in`.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)

        out.close()
        `in`.close()
    }

    @Test
    fun `write test bytes by block`() {
        val expectedByteArray = test.toByteArray()
        val actualByteArray = ByteArray(expectedByteArray.size)

        val out = FileOutputStream(filename)

        out.writeBlock(expectedByteArray, expectedByteArray.size)

        val `in` = FileInputStream(filename)

        `in`.readBlock(actualByteArray, actualByteArray.size)

        assertArrayEquals(expectedByteArray, actualByteArray)

        out.close()
        `in`.close()
    }
}
