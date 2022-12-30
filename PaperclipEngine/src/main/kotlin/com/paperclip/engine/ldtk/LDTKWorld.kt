package com.paperclip.engine.ldtk

import com.paperclip.engine.asset.Asset
import com.paperclip.engine.utils.FileUtils
import kotlin.reflect.KClass

class LDTKWorld : Asset {

    private lateinit var jsonText: String

    override fun createFromPath(kClass: KClass<*>, path: String): Asset {

        println(kClass)

        println(FileUtils.readResourceFileAsString(kClass, path))

        return this
    }

    override fun onDestroy() {

    }

}