package com.paperclip.engine.scene

import com.paperclip.engine.application.Application
import com.paperclip.engine.application.Input

class SceneManager(val application: Application) {

    private var input = Input()

    private var currentScene: Scene? = null

    fun createInput(windowPtr: Long) {
        input.bindCallbacks(windowPtr)
    }

    fun <T: Scene> setScene(constructor: () -> T) = this.setScene(constructor())

    fun <T: Scene> setScene(scene: T) {
        val prevCurrentScene = currentScene

        if(currentScene != null) {
            currentScene = null
            prevCurrentScene?.onDestroy()
        }

        scene.input = this.input
        scene.application = this.application
        scene.onCreate()
        scene.onStart()

        currentScene = scene
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