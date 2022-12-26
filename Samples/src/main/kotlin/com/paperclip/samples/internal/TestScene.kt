package com.paperclip.samples.internal

import com.paperclip.engine.application.Input
import com.paperclip.engine.graphics.Transform
import com.paperclip.engine.math.Vector3f
import com.paperclip.engine.math.Vector4f
import com.paperclip.engine.physics2d.BoxCollider
import com.paperclip.engine.physics2d.CircleCollider
import com.paperclip.engine.physics2d.Rigidbody2D
import com.paperclip.engine.physics2d.RigidbodyType
import com.paperclip.engine.scene.*

class TestScene : GameScene() {

    private val cameraSpeed = 2.0f

    private lateinit var groundEntity: Entity
    private lateinit var groundRigidbody2D: Rigidbody2D

    override fun onCreate() {
        super.onCreate()

        groundEntity = createEntity()
        groundEntity.addComponent(TransformComponent(Transform(Vector3f(0f, -0.75f, 0.0f), Vector3f(2.5f, 0.1f, 0.0f), 0f)))
        groundEntity.addComponent(QuadRendererComponent())

        val rigidbody2D = Rigidbody2D(physicsWorld, RigidbodyType.STATIC)

        groundRigidbody2D = groundEntity.addComponent(rigidbody2D)
        groundEntity.addComponent(BoxCollider())
    }

    var count = 0

    override fun onUpdate(deltaTime: Float) {
        super.onUpdate(deltaTime)

        if(input.isKeyDown(Input.KEY_PERIOD)) {
            val entity = createEntity()
            entity.addComponent(TransformComponent(Transform(Vector3f(0f, 0f, 0f), Vector3f(0.1f, 0.1f, 0.1f), 45f)))
            entity.addComponent(CircleRendererComponent(Vector4f(1.0f, 0.0f, 0.0f, 1.0f)))
            entity.addComponent(Rigidbody2D(physicsWorld))
            entity.addComponent(CircleCollider())
        }

        if(input.isKeyDown(Input.KEY_COMMA)) {
            val entity = createEntity()
            entity.addComponent(TransformComponent(Transform(Vector3f(0f, 0f, 0f), Vector3f(0.1f, 0.1f, 0.1f), 45f)))
            entity.addComponent(QuadRendererComponent(Vector4f(1.0f, 0.0f, 0.0f, 1.0f)))
            entity.addComponent(Rigidbody2D(physicsWorld))
            entity.addComponent(BoxCollider())
        }

        if(input.isKey(Input.KEY_D)) {
            camera.position.x += deltaTime * cameraSpeed
        }

        if(input.isKey(Input.KEY_A)) {
            camera.position.x -= deltaTime * cameraSpeed
        }

        if(input.isKey(Input.KEY_S)) {
            camera.position.y -= deltaTime * cameraSpeed
        }

        if(input.isKey(Input.KEY_W)) {
            camera.position.y += deltaTime * cameraSpeed
        }

        if(input.scrollPosition.y != 0f) {
            camera.zoom -= deltaTime * input.scrollPosition.y
            if(camera.zoom < 0f) camera.zoom = 0f
        }

        if(input.isKey(Input.KEY_ESCAPE)) {
            application.endApplication()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}