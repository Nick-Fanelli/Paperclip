package com.paperclipengine.scene

import com.paperclipengine.application.Input
import com.paperclipengine.application.SingleWindowOpenGLApplication

abstract class Scene {

    internal lateinit var input: Input
    internal lateinit var application: SingleWindowOpenGLApplication

    open fun onCreate() {}
    open fun onUpdate(deltaTime: Float) {}
    open fun onDestroy() {}

    open fun onWindowResize(aspectRatio: Float) {}

}