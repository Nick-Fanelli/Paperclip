package com.paperclip.engine.ldtk

import com.paperclip.engine.asset.Asset
import com.paperclip.engine.utils.FileUtils
import com.paperclip.engine.utils.JSONUtils
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation

class LDTKWorld : Asset {

    private lateinit var jsonText: String

    override fun createFromPath(kClass: KClass<*>, path: String): Asset {

        jsonText = FileUtils.readResourceFileAsString(kClass, path)

        val jsonTarget = LDTKWorldJSONTarget()
        JSONUtils.parseJson(jsonText, jsonTarget)

//        println(jsonTarget)

        return this
    }

    override fun onDestroy() {

    }

}