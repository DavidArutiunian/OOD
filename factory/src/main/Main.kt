package main

import main.canvas.JSwingCanvas
import main.designer.CanvasDesigner
import main.painter.CanvasPainter
import main.shape.CanvasShapeFactory
import java.util.*
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants.EXIT_ON_CLOSE

const val FRAME_TITLE = "PictureDesigner2000"
const val FRAME_WIDTH = 1280
const val FRAME_HEIGHT = 720

fun main() {
    val scanner = Scanner(System.`in`)
    val painter = CanvasPainter()
    val factory = CanvasShapeFactory()
    val designer = CanvasDesigner(factory)
    val canvas = JSwingCanvas()

    try {
        val draft = designer.createDraft(scanner)
        painter.drawPicture(draft, canvas)

        SwingUtilities.invokeLater { initUI(canvas) }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun initUI(canvas: JComponent) {
    val frame = JFrame(FRAME_TITLE)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.contentPane.add(canvas)
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT)
    frame.isVisible = true
    canvas.requestFocus()
}
