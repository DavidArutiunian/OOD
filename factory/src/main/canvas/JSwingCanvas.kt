package main.canvas

import main.Point
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints.KEY_ANTIALIASING
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import javax.swing.JComponent
import java.awt.Shape as JavaAwtShape

class JSwingCanvas : JComponent(), Canvas {
    private var mColor = Color.BLACK
    private val mItems: ArrayList<JavaAwtShape> = ArrayList()

    override fun setColor(color: Color) {
        mColor = color
    }

    override fun drawLine(from: Point, to: Point) {
        val line = Line2D.Double(from.x, from.y, to.x, to.y)
        mItems.add(line)
    }

    override fun drawEllipse(center: Point, width: Double, height: Double) {
        val ellipse = Ellipse2D.Double(center.x, center.y, width, height)
        mItems.add(ellipse)
    }

    override fun paintComponent(g: Graphics?) {
        if (g == null) {
            return
        }

        g.color = Color.LIGHT_GRAY
        g.fillRect(0, 0, width, height)

        val g2d = g as Graphics2D
        g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)

        for (item in mItems) {
            g2d.color = mColor
            g2d.draw(item)
        }
    }
}
