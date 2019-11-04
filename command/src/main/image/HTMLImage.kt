package image

import ext
import md5
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class HTMLImage : Image {
    private var mPath: Path
    private var mWidth: Int
    private var mHeight: Int

    constructor(path: Path, width: Int, height: Int) {
        mPath = path
        mWidth = width
        mHeight = height
    }

    constructor(image: Image) {
        mPath = Path.of(image.getPath().toString())
        mWidth = image.getWidth()
        mHeight = image.getHeight()
    }

    override fun getPath() = mPath

    override fun getWidth() = mWidth

    override fun getHeight() = mHeight

    override fun resize(width: Int, height: Int) {
        mWidth = width
        mHeight = height
    }

    override fun saveTo(basePath: Path, dirPath: Path) {
        val actual = basePath.resolveSibling(dirPath)
        if (Files.notExists(actual)) {
            Files.createDirectory(actual)
        }
        if (!Files.isDirectory(actual)) {
            throw IOException("$actual is not a directory")
        }
        val fileName = mPath.fileName
        val generatedFileName = "${fileName.md5()}.${fileName.ext()}"
        val pathToMovedFile = Path.of("$actual/$fileName")
        val pathToGenFile = Path.of("$actual/$generatedFileName")
        Files.deleteIfExists(pathToGenFile)
        Files.copy(mPath, pathToMovedFile)
        Files.move(pathToMovedFile, pathToGenFile)
        mPath = dirPath.resolve(generatedFileName)
    }
}
