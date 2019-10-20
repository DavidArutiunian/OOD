package image

import java.nio.file.Path

class HTMLImage(private val mPath: Path, private var mWidth: Int, private var mHeight: Int) : Image {
    override fun getPath() = mPath

    override fun getWidth() = mWidth

    override fun getHeight() = mHeight

    override fun resize(width: Int, height: Int) {
        mWidth = width
        mHeight = height
    }
}
