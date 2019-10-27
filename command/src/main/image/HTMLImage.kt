package image

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class HTMLImage(private var mPath: Path, private var mWidth: Int, private var mHeight: Int) : Image {
    override fun getPath() = mPath

    override fun getWidth() = mWidth

    override fun getHeight() = mHeight

    override fun resize(width: Int, height: Int) {
        mWidth = width
        mHeight = height
    }

    override fun saveTo(dir: Path) {
        if (Files.notExists(dir)) {
            Files.createDirectory(dir)
        }
        if (!Files.isDirectory(dir)) {
            throw IOException("$dir is not a directory");
        }
        val pathToSaved = Path.of("$dir/${mPath.fileName}")
        Files.deleteIfExists(pathToSaved)
        Files.copy(mPath, pathToSaved)
        mPath = pathToSaved
    }
}
