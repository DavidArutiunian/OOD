import document.HTMLDocument
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

@Suppress("FunctionName")
internal class HTMLDocumentTest {
    companion object {
        const val MAX_STACK_SIZE = 10
        const val ASSET_PATH = "assets/image.jpg"
        const val TMP_ASSET_PATH = ".tmp/image.jpg"
        const val SAMPLE_TEXT = "Hello, World!"
    }

    @Test
    fun `should insert image`() {
        val document = HTMLDocument()

        document.use {
            document.insertImage(Path.of(ASSET_PATH), 1200, 800)

            val item = document.getItem(0)

            assertTrue(item.getImage() != null)
            assertEquals(Path.of(".tmp/image.jpg").toString(), item.getImage()?.getPath().toString())
            assertEquals(1200, item.getImage()?.getWidth())
            assertEquals(800, item.getImage()?.getHeight())
        }
    }

    @Test
    fun `should insert paragraph`() {
        val document = HTMLDocument()

        document.use {
            document.insertParagraph(SAMPLE_TEXT)

            val item = document.getItem(0)

            assertTrue(item.getParagraph() != null)
            assertEquals(SAMPLE_TEXT, item.getParagraph()?.getText())
        }
    }

    @Test
    fun `should rotate document stack by 10 elements`() {
        val document = HTMLDocument()

        document.use {
            for (i in 0 until MAX_STACK_SIZE + 2) {
                document.insertParagraph("Paragraph #${i + 1}")
            }
            var index = 0

            while (document.canUndo()) {
                document.undo()
                index++
            }

            assertEquals(MAX_STACK_SIZE, index)
        }
    }

    @Test
    fun `should remove image if remove from stack after rotation`() {
        val document = HTMLDocument()

        document.use {
            document.insertImage(Path.of(ASSET_PATH), 1200, 800)

            for (i in 0 until 10) {
                document.insertParagraph("Paragraph #${i + 1}")
            }

            assertFalse(Files.exists(Path.of(TMP_ASSET_PATH)))
        }
    }

    @Test
    fun `should not remove image if insert image was undone`() {
        val document = HTMLDocument()

        document.use {
            document.insertImage(Path.of(ASSET_PATH), 1200, 800)

            document.undo()

            assertTrue(Files.exists(Path.of(TMP_ASSET_PATH)))
        }
    }

    @Test
    fun `should not remove image if delete command was done`() {
        val document = HTMLDocument()

        document.use {
            document.insertImage(Path.of(ASSET_PATH), 1200, 800)

            document.deleteItem(0)

            assertTrue(Files.exists(Path.of(TMP_ASSET_PATH)))
        }
    }

    @Test
    fun `should not remove image if delete command was undone`() {
        val document = HTMLDocument()

        document.use {
            document.insertImage(Path.of(ASSET_PATH), 1200, 800)

            document.deleteItem(0)

            document.undo()

            assertTrue(Files.exists(Path.of(TMP_ASSET_PATH)))
        }
    }

    @Test
    fun `should not remove image if delete command was undone and redone`() {
        val document = HTMLDocument()

        document.use {
            document.insertImage(Path.of(ASSET_PATH), 1200, 800)

            document.deleteItem(0)

            document.undo()
            document.redo()

            assertTrue(Files.exists(Path.of(TMP_ASSET_PATH)))
        }
    }

    @Test
    fun `should save html with paragraph and image`() {
        val document = HTMLDocument()

        document.use {
            val text = SAMPLE_TEXT
            document.insertParagraph(text)

            document.insertImage(Path.of(ASSET_PATH), 1200, 800)

            val html = Path.of("build/index.html")

            document.save(html)

            assertTrue(Files.exists(html))
            val images = "build/images"
            assertTrue(Files.exists(Path.of(images)))
            val files = Files.list(Path.of(images)).toList()
            assertEquals(1, files.size)
            assertEquals(Files.size(files.first()), Files.size(Path.of(ASSET_PATH)))
            val content = Files.readString(html)
            assertTrue(content.contains(text))
        }
    }

    @Test
    fun `should insert with position and undo to previous item`() {
        val document = HTMLDocument()

        document.use {
            for (i in 0 until MAX_STACK_SIZE) {
                document.insertParagraph("Paragraph #${i + 1}")
            }

            document.insertImage(Path.of(ASSET_PATH), 1200, 800, MAX_STACK_SIZE - 1)

            val item = document.getItem(MAX_STACK_SIZE - 1)

            assertTrue(item.getImage() != null)
            assertTrue(item.getParagraph() == null)
        }
    }

    @Test
    fun `should not throw out of bounds if trying to insert into empty document to 0 index`() {
        val document = HTMLDocument()

        document.use {
            val text = SAMPLE_TEXT
            document.insertParagraph(text, 0)

            val item = document.getItem(0)

            val paragraph = item.getParagraph()
            assertTrue(paragraph != null)
            assertEquals(paragraph?.getText(), text)
        }
    }

    @Test
    fun `should throw out of bounds if trying to insert into empty document to 1 index`() {
        val document = HTMLDocument()

        document.use {
            assertThrows<IndexOutOfBoundsException> {
                document.insertParagraph(SAMPLE_TEXT, 1)
            }
        }
    }

    @Test
    fun `should throw out of bounds if trying to delete from empty document to 0 index`() {
        val document = HTMLDocument()

        document.use {
            assertThrows<IndexOutOfBoundsException> {
                document.deleteItem(0)
            }
        }
    }

    @Test
    fun `should throw out of bounds if trying to delete from empty document to 1 index`() {
        val document = HTMLDocument()

        document.use {
            assertThrows<IndexOutOfBoundsException> {
                document.deleteItem(1)
            }
        }
    }
}
