package com.david.arutiunian.lab2.decorators.output

import com.david.arutiunian.lab2.output.OutputStream

abstract class OutputStreamDecoratorImpl(protected val mWrappee: OutputStream) : OutputStream
