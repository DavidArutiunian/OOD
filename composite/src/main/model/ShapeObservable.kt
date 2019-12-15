package model

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.JComponent

class ShapeObservable {
    fun doOnMousePressed(
        canvas: CanvasShape,
        component: JComponent,
        listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit
    ) {
        component.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                listener(canvas, e)
            }
        })
    }

    fun doOnMouseReleased(
        canvas: CanvasShape,
        component: JComponent,
        listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit
    ) {
        component.addMouseListener(object : MouseAdapter() {
            override fun mouseReleased(e: MouseEvent?) {
                listener(canvas, e)
            }
        })
    }

    fun doOnMouseEntered(
        canvas: CanvasShape,
        component: JComponent,
        listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit
    ) {
        component.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent?) {
                listener(canvas, e)
            }
        })
    }

    fun doOnMouseExited(
        canvas: CanvasShape,
        component: JComponent,
        listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit
    ) {
        component.addMouseListener(object : MouseAdapter() {
            override fun mouseExited(e: MouseEvent?) {
                listener(canvas, e)
            }
        })
    }

    fun doOnMouseDragged(
        canvas: CanvasShape,
        component: JComponent,
        listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit
    ) {
        component.addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent?) {
                listener(canvas, e)
            }
        })
    }
}
