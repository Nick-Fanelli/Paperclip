package com.paperclipengine.physics2d

import com.paperclipengine.scene.EntityComponentSystem
import com.paperclipengine.scene.TransformComponent
import org.joml.Vector2f
import org.joml.Vector3f

data class PhysicsComponentData(val transformComponent: TransformComponent, val rigidbody2D: Rigidbody2D)

class Physics2DWorld(private val entityComponentSystem: EntityComponentSystem) {

    var gravity = Vector2f(0.0f, -9.8f)

    private val physicsTimeStep = 1.0f / 60.0f

    private var physicsComponentData: ArrayList<PhysicsComponentData> = ArrayList()

    private var physicsTime = 0.0f

    init {
        entityComponentSystem.addComponentTypeListener<Rigidbody2D> { updatePhysicsComponentData() }
    }

    fun onUpdate(deltaTime: Float) {
        physicsTime += deltaTime
        if(physicsTime >= 0.0f) {
            physicsTime -= physicsTimeStep
            step()
        }
    }

    private fun updatePhysicsComponentData() {
        physicsComponentData.clear()

        entityComponentSystem.forEachComponentByType<Rigidbody2D, TransformComponent>(TransformComponent::class) {
            physicsComponentData.add(PhysicsComponentData(it.second, it.first))
        }
    }

    private fun step() {
        physicsComponentData.forEach {
            updateEntity(it)
        }
    }

    private fun updateEntity(entity: PhysicsComponentData) {

        // Update the position of the entity by its velocity
        val scaledVelocity = Vector3f(entity.rigidbody2D.velocity.x, entity.rigidbody2D.velocity.y, 0.0f).mul(physicsTimeStep)
        entity.transformComponent.transform.position.add(scaledVelocity)
    }

}