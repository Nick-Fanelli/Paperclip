package com.paperclip.engine.scene

import com.paperclip.engine.application.Input
import com.paperclip.engine.application.SingleWindowOpenGLApplication

abstract class Scene {

    lateinit var input: Input
    lateinit var application: SingleWindowOpenGLApplication

    open fun onCreate() {}
    open fun onUpdate(deltaTime: Float) {}
    open fun onDestroy() {}

    open fun onWindowResize(aspectRatio: Float) {}
}