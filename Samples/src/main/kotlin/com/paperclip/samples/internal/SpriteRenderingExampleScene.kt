package com.paperclip.samples.internal

import com.paperclip.engine.application.Application
import com.paperclip.engine.application.Input
import com.paperclip.engine.graphics.Texture
import com.paperclip.engine.scene.*
import com.paperclip.engine.utils.RuntimeConfig

fun main() {
    Application(SpriteRenderingExampleScene::class, "Image Loading Example Scene", ::SpriteRenderingExampleScene)
        .startApplication()
}

private class ImageEntity(entity: Entity) {

    val transformComponent = entity.addComponent(TransformComponent())
    val quadRendererComponent = entity.addComponent(QuadRendererComponent())

}

class SpriteRenderingExampleScene : GameScene() {

    private lateinit var minecraftGrassTexture: Texture

    private lateinit var imageEntity: ImageEntity

    override fun onCreate() {
        super.onCreate()

        minecraftGrassTexture = assetManager.get(Texture::class, "/textures/minecraft-grass.png")

        imageEntity = ImageEntity(createEntity())

        println(RuntimeConfig.OpenGLRuntimeConfig.availableGUPTextureSlots)
    }

    override fun onUpdate(deltaTime: Float) {
        super.onUpdate(deltaTime)

        // Close application when escape key is pressed
        if(input.isKeyDown(Input.KEY_ESCAPE)) {
            application.endApplication()
        }
    }

}