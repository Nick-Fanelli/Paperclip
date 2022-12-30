package com.paperclip.engine.utils

import kotlin.reflect.KClass

internal object FileUtils {

    internal fun readResourceFileAsString(filepath: String) : String = BuildConfig::class.java.getResource(filepath)!!.readText(Charsets.UTF_8)
    internal fun readResourceFileAsBytes(filepath: String) : ByteArray = BuildConfig::class.java.getResource(filepath)!!.readBytes()

    internal fun readResourceFileAsString(clazz: KClass<*>, filepath: String) : String {
        val text = clazz.java.getResource(filepath) ?: throw RuntimeException("Text is null")

        return clazz.java.getResource(filepath)!!.readText(Charsets.UTF_8)
    }

}
