package image

import java.nio.file.Path

interface Image {
    fun getPath(): Path

    fun getWidth(): Int

    fun getHeight(): Int

    fun resize(width: Int, height: Int)
}
