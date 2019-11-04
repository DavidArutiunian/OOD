import document.HTMLDocument
import java.nio.file.Path

fun main() {
    val document = HTMLDocument()

    document.use {
        document.setTitle("HTML Document")

        document.insertParagraph("Hello, World!")

        document.list(System.out)

        document.insertParagraph(
            "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\""
        )

        document.list(System.out)

        if (document.canUndo()) {
            document.undo()
        }

        document.list(System.out)

        if (document.canRedo()) {
            document.redo()
        }

        document.list(System.out)

        document.insertImage(Path.of("assets/image.jpg"), 1200, 800)

        document.list(System.out)

        document.insertParagraph("Replaced Image", 2)

        document.list(System.out)

        document.replaceText(2, "Replaced Image with Replaced Text")

        document.list(System.out)

        if (document.canUndo()) {
            document.undo()
        }

        document.list(System.out)

        document.resizeImage(2, 600, 400)

        document.list(System.out)

        if (document.canRedo()) {
            document.redo()
        }

        document.list(System.out)

        if (document.canUndo()) {
            document.undo()
        }

        document.deleteItem(2)

        if (document.canUndo()) {
            document.undo()
        }

        if (document.canRedo()) {
            document.redo()
        }

        document.list(System.out)

        if (document.canUndo()) {
            document.undo()
        }

        document.save(Path.of("build/index.html"))
    }
}
