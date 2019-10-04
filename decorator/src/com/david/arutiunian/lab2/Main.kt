package com.david.arutiunian.lab2

import com.david.arutiunian.lab2.decorators.input.DecompressInputStream
import com.david.arutiunian.lab2.decorators.input.DecryptInputStream
import com.david.arutiunian.lab2.decorators.output.CompressOutputStream
import com.david.arutiunian.lab2.decorators.output.EncryptOutputStream
import com.david.arutiunian.lab2.input.FileInputStream
import com.david.arutiunian.lab2.output.FileOutputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    val inputFileName = args[args.size - 2]
    val outputFileName = args[args.size - 1]
    val tempInputFileName = "${outputFileName}.tmp"

    for (i in 0 until args.size - 2) {
        when (args[i]) {
            "--encrypt" -> {
                swapToTempFile(outputFileName, inputFileName, tempInputFileName)
                val fileInputStream = FileInputStream(tempInputFileName)
                val fileOutputStream = FileOutputStream(outputFileName)
                val seed = args[i + 1].toInt()
                val encryptOutputStream = EncryptOutputStream(fileOutputStream, seed)
                while (!fileInputStream.isEOF()) {
                    val byte = fileInputStream.readByte()
                    encryptOutputStream.writeByte(byte)
                }
                fileInputStream.close()
                fileOutputStream.close()
            }
            "--decrypt" -> {
                swapToTempFile(outputFileName, inputFileName, tempInputFileName)
                val fileInputStream = FileInputStream(tempInputFileName)
                val fileOutputStream = FileOutputStream(outputFileName)
                val seed = args[i + 1].toInt()
                val decryptInputStream = DecryptInputStream(fileInputStream, seed)
                while (!decryptInputStream.isEOF()) {
                    val byte = decryptInputStream.readByte()
                    fileOutputStream.writeByte(byte)
                }
                fileInputStream.close()
                fileOutputStream.close()
            }
            "--compress" -> {
                swapToTempFile(outputFileName, inputFileName, tempInputFileName)
                val fileInputStream = FileInputStream(tempInputFileName)
                val fileOutputStream = FileOutputStream(outputFileName)
                val compressOutputStream = CompressOutputStream(fileOutputStream)
                while (!fileInputStream.isEOF()) {
                    val byte = fileInputStream.readByte()
                    compressOutputStream.writeByte(byte)
                }
                compressOutputStream.close()
                fileInputStream.close()
                fileOutputStream.close()
            }
            "--decompress" -> {
                swapToTempFile(outputFileName, inputFileName, tempInputFileName)
                val fileInputStream = FileInputStream(tempInputFileName)
                val fileOutputStream = FileOutputStream(outputFileName)
                val decompressInputStream = DecompressInputStream(fileInputStream)
                while (!decompressInputStream.isEOF()) {
                    val byte = decompressInputStream.readByte()
                    fileOutputStream.writeByte(byte)
                }
                fileInputStream.close()
                fileOutputStream.close()
            }
        }
    }

    Files.delete(Path.of(tempInputFileName))
}

private fun swapToTempFile(outputFileName: String, inputFileName: String, tempInputFileName: String) {
    val file = File(outputFileName)
    val fileInputStream = FileInputStream(if (file.length() == 0L) inputFileName else outputFileName)
    val fileOutputStream = FileOutputStream(tempInputFileName)
    while (!fileInputStream.isEOF()) {
        val byte = fileInputStream.readByte()
        fileOutputStream.writeByte(byte)
    }
    fileInputStream.close()
    fileOutputStream.close()
}
