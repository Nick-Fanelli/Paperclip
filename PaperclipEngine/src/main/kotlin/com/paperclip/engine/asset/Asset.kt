package com.paperclip.engine.asset

import kotlin.reflect.KClass

interface Asset {

    fun createFromPath(kClass: KClass<*>, path: String) : Asset
    fun onDestroy()

}