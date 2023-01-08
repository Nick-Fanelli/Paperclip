package com.paperclip.engine.application

import com.paperclip.engine.asset.AssetManager
import com.paperclip.engine.scene.Scene
import com.paperclip.engine.scene.SceneManager
import com.paperclip.engine.utils.Logger
import com.paperclip.engine.utils.RuntimeConfig
import kotlin.reflect.KClass

open class Application( private val projectScope: KClass<*>,
                        applicationName: String,
                        private val startingScene: (() -> Scene)? = null,
                        displayPreferences: DisplayPreferences = DisplayPreferences()) {

    val display = Display(applicationName, displayPreferences)

    lateinit var assetManager: AssetManager
        private set

    private lateinit var sceneManager: SceneManager

    init {
        RuntimeConfig.initialize()
        display.createDisplay(::onUpdate)
    }

    fun startApplication() {
        assetManager = AssetManager(this, projectScope)
        assetManager.initialize()

        sceneManager = SceneManager(this)
        sceneManager.createInput(display.windowPtr)

        display.addWindowResizeCallback(::windowResize)

        if(startingScene != null)
            sceneManager.setScene(startingScene)

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
        assetManager.destroy()
        display.cleanUp()
    }

    private fun windowResize(_width: Int, _height: Int) = sceneManager.onWindowResize(display.aspectRatio)

}