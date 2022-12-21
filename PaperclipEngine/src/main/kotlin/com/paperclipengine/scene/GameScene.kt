package com.paperclipengine.scene

import com.paperclipengine.graphics.camera.OrthographicCamera
import com.paperclipengine.graphics.render.QuadRenderer
import com.paperclipengine.physics2d.Physics2DWorld

open class GameScene : Scene() {

    protected lateinit var camera: OrthographicCamera

    private val entityComponentSystem = EntityComponentSystem()
    private val physicsWorld = Physics2DWorld(entityComponentSystem)

    private var quadRenderers: ArrayList<ComponentPair<QuadRendererComponent, TransformComponent>> = ArrayList()

    private lateinit var quadRenderer: QuadRenderer
    fun createEntity() : Entity = entityComponentSystem.createEntity()
    fun destroyEntity(entity: Entity) = entityComponentSystem.destroyEntity(entity)

    override fun onCreate() {
        super.onCreate()

        camera = OrthographicCamera()

        quadRenderer = QuadRenderer(this, camera)
        quadRenderer.create()

        entityComponentSystem.addComponentTypeListener<QuadRendererComponent> { updateQuadRendererComponents() }
    }

    override fun onUpdate(deltaTime: Float) {

        physicsWorld.onUpdate(deltaTime)

    }

    private fun updateQuadRendererComponents() {
        quadRenderers = entityComponentSystem.getAllComponentsByType<QuadRendererComponent, TransformComponent>(TransformComponent::class)
    }

    protected fun onRender() {
        quadRenderer.begin()

        quadRenderers.forEach {
            quadRenderer.drawQuad(it.second.transform, it.first.color)
        }

        quadRenderer.end()
    }

    override fun onDestroy() {
        super.onDestroy()

        quadRenderer.destroy()
    }

    override fun onWindowResize(aspectRatio: Float) {
        camera.aspectRatio = aspectRatio
    }

}