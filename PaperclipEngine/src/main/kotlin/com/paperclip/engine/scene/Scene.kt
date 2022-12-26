package com.paperclip.engine.scene

import com.paperclip.engine.application.Application
import com.paperclip.engine.application.Input

abstract class Scene {

    lateinit var input: Input
    lateinit var application: Application

    open fun onCreate() {}
    open fun onUpdate(deltaTime: Float) {}
    open fun onRender() {}
    open fun onDestroy() {}

    open fun onWindowResize(aspectRatio: Float) {}
}