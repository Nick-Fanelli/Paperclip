package com.paperclipengine.scene

import com.paperclipengine.application.Input

abstract class Scene {

    internal lateinit var input: Input

    open fun onCreate() {}
    open fun onUpdate(deltaTime: Float) {}
    open fun onRender() {}
    open fun onDestroy() {}

}