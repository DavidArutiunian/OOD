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

fun String.escape(): String {
    val out = StringBuilder(kotlin.math.max(16, this.length))
    for (element in this) {
        if (element.toInt() > 127 || element == '"' || element == '<' || element == '>' || element == '&') {
            out.append("&#")
            out.append(element.toInt())
            out.append(';')
        } else {
            out.append(element)
        }
    }
    return out.toString()
}