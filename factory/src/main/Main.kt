package main

import main.canvas.JSwingCanvas
import main.designer.DesignerImpl
import main.painter.PainterImpl
import main.shape.ShapeFactoryImpl
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
    val painter = PainterImpl()
    val factory = ShapeFactoryImpl()
    val designer = DesignerImpl(factory)
    val canvas = JSwingCanvas()

    try {
        val draft = designer.createDraft(scanner)
        painter.drawPicture(draft, canvas)

        SwingUtilities.invokeLater { initUI(canvas) }
    } catch (ex: Exception) {
        println("OOPS...")
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
