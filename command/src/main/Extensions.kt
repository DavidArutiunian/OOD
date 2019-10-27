import java.io.File
import java.math.BigInteger
import java.nio.file.Path
import java.security.MessageDigest
import java.util.*


fun Path.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toString().toByteArray())).toString(16).padStart(32, '0')
}

fun Path.ext(): String {
    val filename = toString()
    return Optional.ofNullable(filename)
        .filter { f -> f.contains(".") }
        .map { f -> f.substring(filename.lastIndexOf(".") + 1) }
        .get()
}

fun deleteDirectory(dir: File): Boolean {
    if (dir.isDirectory) {
        val children = dir.listFiles()
        if (children.isNullOrEmpty()) {
            return dir.delete()
        }
        for (child in children) {
            val success = deleteDirectory(child)
            if (!success) {
                return false;
            }
        }
    }
    return dir.delete()
}
