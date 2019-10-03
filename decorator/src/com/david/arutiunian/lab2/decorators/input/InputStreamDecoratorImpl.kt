package com.david.arutiunian.lab2.decorators.input

import com.david.arutiunian.lab2.input.InputStream

abstract class InputStreamDecoratorImpl(protected val mWrappee: InputStream) : InputStream {
    override fun isEOF() = mWrappee.isEOF()
}
