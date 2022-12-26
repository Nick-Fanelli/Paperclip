package com.paperclip.engine.scene

import com.paperclip.engine.application.Input
import com.paperclip.engine.application.SingleWindowOpenGLApplication

class SceneManager(val application: SingleWindowOpenGLApplication) {

    private var input = Input()

    private var currentScene: Scene? = null

    fun createInput(windowPtr: Long) {
        input.bindCallbacks(windowPtr)
    }

    fun <T: Scene> setScene(constructor: () -> T) {
        val prevCurrentScene = currentScene

        if(currentScene != null) {
            currentScene = null
            prevCurrentScene?.onDestroy()
        }

        val instantiatedScene: T = constructor()

        instantiatedScene.input = this.input
        instantiatedScene.application = this.application
        instantiatedScene.onCreate()

        currentScene = instantiatedScene
    }

    fun onUpdate(deltaTime: Float) {
        currentScene?.onUpdate(deltaTime)
        currentScene?.onRender()
        input.update()
    }

    internal fun onWindowResize(aspectRatio: Float) {
        currentScene?.onWindowResize(aspectRatio)
    }

    fun onDestroy() {
        currentScene?.onDestroy()
    }

}