package com.paperclipengine.scene

import com.paperclipengine.application.Input
import com.paperclipengine.application.SingleWindowOpenGLApplication

abstract class Scene {

    lateinit var input: Input
    lateinit var application: SingleWindowOpenGLApplication

    open fun onCreate() {}
    open fun onUpdate(deltaTime: Float) {}
    open fun onDestroy() {}

    open fun onWindowResize(aspectRatio: Float) {}

}