package main.designer

import java.util.*

interface Designer {
    fun createDraft(scanner: Scanner): PictureDraft
}
