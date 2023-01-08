package com.paperclip.engine.utils

import kotlin.reflect.KClass

internal object FileUtils {

    internal fun readResourceFileAsString(filepath: String) = readResourceFileAsString(BuildConfig::class, filepath)
    internal fun readResourceFileAsBytes(filepath: String) = FileUtils.readResourceFileAsBytes(BuildConfig::class, filepath)

    internal fun readResourceFileAsString(kClass: KClass<*>, filepath: String) = kClass.java.getResource(filepath)!!.readText(Charsets.UTF_8)
    internal fun readResourceFileAsBytes(kClass: KClass<*>, filepath: String) =  kClass.java.getResource(filepath)!!.readBytes()
}
