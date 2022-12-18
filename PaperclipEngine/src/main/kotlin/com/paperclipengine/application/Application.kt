package com.paperclipengine.application

abstract class Application(private val applicationName: String) {

    abstract fun startApplication()
    internal abstract fun onUpdate(deltaTime: Float)

}