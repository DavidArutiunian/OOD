package model

import java.awt.event.MouseEvent

interface CanvasShapeObservable {
    fun doOnMousePressed(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit)

    fun doOnMouseReleased(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit)

    fun doOnMouseEntered(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit)

    fun doOnMouseExited(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit)

    fun doOnMouseDragged(listener: (canvas: CanvasShape, event: MouseEvent?) -> Unit)

    fun doOnMouseDragged(diff: FrameRect)
}
