package com.paperclipengine.application

import com.paperclipengine.scene.Scene
import com.paperclipengine.scene.SceneManager

class SingleWindowOpenGLApplication(private val applicationName: String, private val startingScene: (() -> Scene)? = null, displayPreferences: DisplayPreferences = DisplayPreferences()) : SingleWindowApplication(applicationName, displayPreferences) {

    private lateinit var sceneManager: SceneManager

    override fun startApplication() {
        sceneManager = SceneManager(this)
        sceneManager.createInput(display.windowPtr)

        if(startingScene != null)
            sceneManager.setScene(startingScene)

        display.windowResizeCallback = ::windowResize

        super.startApplication()
    }

    fun endApplication() {
        display.closeDisplay()
    }

    private fun windowResize(aspectRatio: Float) {
        sceneManager.onWindowResize(aspectRatio)
    }

    override fun onUpdate(deltaTime: Float) {
        sceneManager.onUpdate(deltaTime)
    }

    override fun onDestroy() {
        sceneManager.onDestroy()

        super.onDestroy()
    }

}