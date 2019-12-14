import gui.GUI
import javax.swing.SwingUtilities

const val width = 800
const val height = 600
const val title = "paint.da"

fun main() {
    SwingUtilities.invokeLater {
        val canvas = GUI(title, width, height)
        canvas.requestFocus()
    }
}
