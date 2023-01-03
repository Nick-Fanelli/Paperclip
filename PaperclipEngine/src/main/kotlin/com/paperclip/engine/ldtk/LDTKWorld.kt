package com.paperclip.engine.ldtk

import com.paperclip.engine.asset.Asset
import com.paperclip.engine.utils.FileUtils
import com.paperclip.engine.utils.JSONUtils
import kotlin.reflect.KClass

class LDTKWorld : Asset {

    private lateinit var jsonText: String

    override fun createFromPath(kClass: KClass<*>, path: String): Asset {

        jsonText = FileUtils.readResourceFileAsString(kClass, path)

        val parsedJSONObject = JSONUtils.parseJson(jsonText)
        val jsonTarget = JSONUtils.jsonToObject<LDTKWorldJSONTarget>(parsedJSONObject)

        this.createFromJSONObject(jsonTarget)

        return this
    }

    private fun createFromJSONObject(json: LDTKWorldJSONTarget) {

        json.levels.forEach {
            println(it)
        }

    }

    override fun onDestroy() {

    }

}