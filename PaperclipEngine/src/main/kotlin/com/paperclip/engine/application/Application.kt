package com.paperclip.engine.application

import com.paperclip.engine.scene.Scene
import com.paperclip.engine.scene.SceneManager
import com.paperclip.engine.utils.Logger

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

        display.addWindowResizeCallback(::windowResize)

        Logger.info("Starting Display Continuous Loop")
        display.continuouslyUpdateDisplayUntilCloseRequested()

        this.onDestroy()
    }

    fun endApplication() {
        Logger.info("End of application requested")
        display.closeDisplay()
    }

    private fun onUpdate(deltaTime: Float) {
        sceneManager.onUpdate(deltaTime)
    }

    private fun onDestroy() {
        sceneManager.onDestroy()
        display.cleanUp()
    }

    private fun windowResize(_width: Int, _height: Int) = sceneManager.onWindowResize(display.aspectRatio)

}