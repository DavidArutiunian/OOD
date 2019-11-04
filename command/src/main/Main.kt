import document.HTMLDocument
import java.nio.file.Path

fun main() {
    val document = HTMLDocument()

    document.use {
        document.setTitle("HTML Document")

        document.insertParagraph("Hello, World!")

        document.insertParagraph(
            "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\""
        )

        document.insertImage(Path.of("assets/image.jpg"), 1200, 800)

        document.list(System.out)

        document.save(Path.of("build/index.html"))
    }
}
