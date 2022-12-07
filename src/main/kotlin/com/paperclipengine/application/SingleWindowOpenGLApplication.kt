package com.paperclipengine.application

import com.paperclipengine.graphics.MeshFactory
import com.paperclipengine.scene.Scene
import com.paperclipengine.scene.SceneManager

class SingleWindowOpenGLApplication(private val applicationName: String, private val startingScene: (() -> Scene)? = null) : SingleWindowApplication(applicationName) {

    private lateinit var sceneManager: SceneManager

    override fun startApplication() {
        sceneManager = SceneManager()
        sceneManager.createInput(display.windowPtr)

        if(startingScene != null)
            sceneManager.setScene(startingScene)

        super.startApplication()
    }

    override fun onUpdate(deltaTime: Float) {
        sceneManager.onUpdate(deltaTime)
    }

    override fun onDestroy() {
        sceneManager.onDestroy()

        MeshFactory.cleanUp()

        super.onDestroy()
    }

}