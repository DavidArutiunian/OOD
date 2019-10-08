package main.designer

import java.util.*

interface Designer {
    fun createDraft(stream: Scanner): PictureDraft
}
