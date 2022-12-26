package com.paperclip.engine.application

import com.paperclip.engine.scene.Scene
import com.paperclip.engine.scene.SceneManager

open class Application(applicationName: String, private val startingScene: (() -> Scene)? = null, displayPreferences: DisplayPreferences = DisplayPreferences()) {

    val display = Display(applicationName, displayPreferences)

    private lateinit var sceneManager: SceneManager

    init {
        display.createDisplay(::onUpdate)
    }

    fun startApplication() {
        sceneManager = SceneManager(this)
        sceneManager.createInput(display.windowPtr)

        if(startingScene != null)
            sceneManager.setScene(startingScene)

        display.windowResizeCallback = ::windowResize

        display.continuouslyUpdateDisplayUntilCloseRequested()
    }

    fun endApplication() {
        display.closeDisplay()
    }

    private fun onUpdate(deltaTime: Float) {
        sceneManager.onUpdate(deltaTime)
    }

    internal fun onDestroy() {
        sceneManager.onDestroy()
    }

    private fun windowResize(aspectRatio: Float) = sceneManager.onWindowResize(aspectRatio)

}