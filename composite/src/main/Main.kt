import gui.Application
import javax.swing.SwingUtilities

const val width = 800
const val height = 600
const val title = "paint.da"

fun main() {
    SwingUtilities.invokeLater {
        val app = Application.getInstance(title, width, height)
        app.requestFocus()
    }
}
