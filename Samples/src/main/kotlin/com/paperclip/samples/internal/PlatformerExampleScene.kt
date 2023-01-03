package com.paperclip.samples.internal

import com.paperclip.engine.application.Application
import com.paperclip.engine.application.Input
import com.paperclip.engine.graphics.Transform
import com.paperclip.engine.ldtk.LDTKWorld
import com.paperclip.engine.math.Vector3f
import com.paperclip.engine.scene.*

fun main() {
    val application = Application(PlatformerExampleScene::class, "Platformer Example", ::PlatformerExampleScene)
    application.startApplication()
}

class PlatformerExampleScene : GameScene() {

    private lateinit var world: LDTKWorld

    override fun onCreate() {
        super.onCreate()

//        world = assetManager.get(LDTKWorld::class, "/platformer.ldtk")
    }

    override fun onUpdate(deltaTime: Float) {
        super.onUpdate(deltaTime)

        if(input.isKeyDown(Input.KEY_ESCAPE)) {
            super.application.endApplication()
        }

    }

}