package com.paperclipengine.utils

internal object FileUtils {

    internal fun readResourceFileAsString(filepath: String) : String = FileUtils::class.java.getResource(filepath)!!.readText(Charsets.UTF_8)
    internal fun readResourceFileAsBytes(filepath: String) : ByteArray = FileUtils::class.java.getResource(filepath)!!.readBytes()

}
