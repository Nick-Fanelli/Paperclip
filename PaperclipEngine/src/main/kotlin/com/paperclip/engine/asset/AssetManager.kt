package com.paperclip.engine.asset

import com.paperclip.engine.application.Application
import com.paperclip.engine.utils.PaperclipEngineException
import kotlin.reflect.KClass

class AssetManager(private val application: Application, private val projectScope: KClass<*>) {

    private val assets = HashMap<String, Asset>()

    fun <T: Asset> get(classType: KClass<T>, filepath: String) : T {
        if(assets.containsKey(filepath)) {

            if(assets[filepath]!!::class != classType)
                throw PaperclipEngineException("Asset Type is not assignable")

            return assets[filepath]!! as T
        }

        assets[filepath] = classType.java.getDeclaredConstructor().newInstance()
        assets[filepath]!!.createFromPath(projectScope, filepath)

        return assets[filepath]!! as T
    }

    internal fun initialize() {

    }

    internal fun destroy() {
        for(asset in assets) {
            asset.value.onDestroy()
        }

        assets.clear()
    }

}