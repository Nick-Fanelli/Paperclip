package com.paperclip.samples.internal

/*
 * This simple simulation allows you to spawn in circle and square entities that react with the ground entity
 *
 * -------- Controls --------
 * WASD     - to move around the world
 * SCROLL   - zoom in and out
 * PERIOD   - spawn in a circle entity
 * COMMA    - spawn in a square entity
 *
 */

import com.paperclip.engine.application.Application
import com.paperclip.engine.application.Input
import com.paperclip.engine.graphics.Transform
import com.paperclip.engine.math.Vector3f
import com.paperclip.engine.math.Vector4f
import com.paperclip.engine.physics2d.BoxCollider
import com.paperclip.engine.physics2d.CircleCollider
import com.paperclip.engine.physics2d.Rigidbody2D
import com.paperclip.engine.physics2d.RigidbodyType
import com.paperclip.engine.scene.*

// Main method to start the program
fun main() {
    // Create the application and link the scene
    val application = Application("Paperclip Engine", ::PhysicsSimulationScene)

    // Start the Application
    application.startApplication()
}

// Game Scene
class PhysicsSimulationScene : GameScene() {

    private val cameraSpeed = 2.0f // How fast the camera will move

    private lateinit var groundEntity: Entity // Ground entity that others will interact with (static + doesn't move)

    override fun onCreate() {
        super.onCreate()

        // Create the ground entity
        groundEntity = createEntity()
        // Add a transform component to specify where it is and what size it is
        groundEntity.addComponent(TransformComponent(Transform(Vector3f(0f, -0.75f, 0.0f), Vector3f(2.5f, 0.1f, 0.0f), 0f)))
        // Add a quad renderer component so it gets rendered as a quad
        groundEntity.addComponent(QuadRendererComponent())

        // Create a static rigidbody for the ground entity, so it is not affected by any forces
        val rigidbody2D = Rigidbody2D(physicsWorld, RigidbodyType.STATIC)

        // Apply the rigidbody component created above
        groundEntity.addComponent(rigidbody2D)
        // Add a box collider component so other entities can collide with it. (box collider automatically figures out the size based on tranfsorm component)
        groundEntity.addComponent(BoxCollider())
    }

    override fun onUpdate(deltaTime: Float) {
        super.onUpdate(deltaTime)

        // Spawn in a circle entity when period is pressed
        if(input.isKeyDown(Input.KEY_PERIOD)) {
            val entity = createEntity()
            entity.addComponent(TransformComponent(Transform(Vector3f(0f, 0f, 0f), Vector3f(0.1f, 0.1f, 0.1f), 45f)))
            entity.addComponent(CircleRendererComponent(Vector4f(1.0f, 0.0f, 0.0f, 1.0f)))
            entity.addComponent(Rigidbody2D(physicsWorld))
            entity.addComponent(CircleCollider())
        }

        // Spawn in a square entity when comma is pressed
        if(input.isKeyDown(Input.KEY_COMMA)) {
            val entity = createEntity()
            entity.addComponent(TransformComponent(Transform(Vector3f(0f, 0f, 0f), Vector3f(0.1f, 0.1f, 0.1f), 45f)))
            entity.addComponent(QuadRendererComponent(Vector4f(1.0f, 0.0f, 0.0f, 1.0f)))
            entity.addComponent(Rigidbody2D(physicsWorld))
            entity.addComponent(BoxCollider())
        }

        // -- BEGIN Movement Code
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
        // -- END Movement Code

        // End the application when escape is pressed
        if(input.isKey(Input.KEY_ESCAPE))
            application.endApplication()
    }

}