package gui.state

import java.awt.Color
import java.awt.event.MouseEvent

interface ShapeState {
    fun handlePress(event: MouseEvent)

    fun handleDrag(event: MouseEvent)

    fun handleRelease(event: MouseEvent)

    fun fillColorChanged(color: Color)

    fun strokeColorChanged(color: Color)
}
