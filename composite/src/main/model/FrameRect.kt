package model

import java.awt.Point

data class FrameRect(val topLeft: Point, val bottomRight: Point) {
    operator fun plusAssign(other: FrameRect) {
        topLeft += other.topLeft
        bottomRight += other.bottomRight
    }
}

operator fun Point.plusAssign(other: Point) {
    x += other.x
    y += other.y
}
