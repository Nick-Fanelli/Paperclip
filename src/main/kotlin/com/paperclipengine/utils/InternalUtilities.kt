package com.paperclipengine.utils

import java.io.File

internal object InternalUtilities {

    internal object FileUtils {

        internal fun readResourceFileAsString(filepath: String) : String = InternalUtilities::class.java.getResource(filepath)!!.readText(Charsets.UTF_8)
        internal fun readResourceFileAsBytes(filepath: String) : ByteArray = InternalUtilities::class.java.getResource(filepath)!!.readBytes()

    }
}