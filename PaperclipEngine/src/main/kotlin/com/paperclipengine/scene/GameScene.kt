package com.paperclipengine.scene

import com.paperclipengine.graphics.Transform
import com.paperclipengine.graphics.camera.OrthographicCamera
import com.paperclipengine.graphics.render.QuadRenderer
import org.joml.Vector3f
import org.joml.Vector4f

open class GameScene : Scene() {

    protected lateinit var camera: OrthographicCamera

    private val entityComponentSystem = EntityComponentSystem()

    private lateinit var quadRenderer: QuadRenderer
    fun createEntity() : Entity = entityComponentSystem.createEntity()
    fun destroyEntity(entity: Entity) = entityComponentSystem.destroyEntity(entity)

    override fun onCreate() {
        super.onCreate()

        camera = OrthographicCamera()

        quadRenderer = QuadRenderer(this, camera)
        quadRenderer.create()
    }

    protected fun onRender() {
        quadRenderer.begin()

        entityComponentSystem.forEachComponentByType<QuadRendererComponent, TransformComponent>(TransformComponent::class) {
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