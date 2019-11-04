import java.io.File

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
