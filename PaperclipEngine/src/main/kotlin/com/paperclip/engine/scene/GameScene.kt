package com.paperclip.engine.scene

import com.paperclip.engine.asset.AssetManager
import com.paperclip.engine.graphics.Texture
import com.paperclip.engine.math.Transform
import com.paperclip.engine.graphics.camera.OrthographicCamera
import com.paperclip.engine.graphics.render.CircleRenderer
import com.paperclip.engine.graphics.render.QuadRenderer
import com.paperclip.engine.math.Vector3f
import com.paperclip.engine.math.Vector4f
import com.paperclip.engine.physics2d.Physics2DWorld

open class GameScene : Scene() {

    protected lateinit var camera: OrthographicCamera

    protected lateinit var assetManager: AssetManager
        private set

    private val entityComponentSystem = EntityComponentSystem()
    val physicsWorld = Physics2DWorld(entityComponentSystem)

    private var quadRenderers: ArrayList<ComponentPair<QuadRendererComponent, TransformComponent>> = ArrayList()
    private var circleRenderers: ArrayList<ComponentPair<CircleRendererComponent, TransformComponent>> = ArrayList()

    private lateinit var quadRenderer: QuadRenderer
    private lateinit var circleRenderer: CircleRenderer

    fun createEntity() : Entity = entityComponentSystem.createEntity()
    fun destroyEntity(entity: Entity) = entityComponentSystem.destroyEntity(entity)

    override fun onCreate() {
        super.onCreate()

        this.assetManager = super.application.assetManager

        camera = OrthographicCamera()

        quadRenderer = QuadRenderer(this, camera)
        quadRenderer.create()

        circleRenderer = CircleRenderer(this, camera)
        circleRenderer.create()

        entityComponentSystem.addComponentTypeListener<QuadRendererComponent> {
            quadRenderers = entityComponentSystem.getAllComponentsByType(TransformComponent::class)
        }

        entityComponentSystem.addComponentTypeListener<CircleRendererComponent> {
            circleRenderers = entityComponentSystem.getAllComponentsByType(TransformComponent::class)
        }

        this.onRenderLoadingScreen()
    }

    open fun onRenderLoadingScreen() {

        val paperclipBannerTexture = Texture()
        paperclipBannerTexture.createFromPath(this::class, "/logos/paperclip-banner-transparent.png")

        application.display.requestUpdate {
            quadRenderer.begin()
            quadRenderer.drawQuad(Transform(Vector3f(0.0f), Vector3f(2.325f, 0.5f, 0.0f)), Vector4f(1.0f), paperclipBannerTexture)
            quadRenderer.end()
        }

        paperclipBannerTexture.onDestroy()
    }

    override fun onUpdate(deltaTime: Float) {
        physicsWorld.onUpdate(deltaTime)
    }

    override fun onRender() {
        quadRenderer.begin()
        quadRenderers.forEach {
            if(it.first.isActive)
                quadRenderer.drawQuad(it.second.transform, it.first.color, it.first.texture)
        }
        quadRenderer.end()

        circleRenderer.begin()
        circleRenderers.forEach {
            circleRenderer.drawCircle(it.second.transform, it.first.color)
        }
        circleRenderer.end()
    }

    override fun onDestroy() {
        super.onDestroy()

        quadRenderer.destroy()
        circleRenderer.destroy()
    }

    override fun onWindowResize(aspectRatio: Float) {
        camera.aspectRatio = aspectRatio
    }

}