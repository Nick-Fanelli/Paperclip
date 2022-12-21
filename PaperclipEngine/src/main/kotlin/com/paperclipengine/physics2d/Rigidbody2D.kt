package com.paperclipengine.physics2d

import com.paperclipengine.scene.Component
import com.paperclipengine.scene.EntityComponentSystem
import com.paperclipengine.scene.TransformComponent
import org.joml.Vector2f

class Rigidbody2D : Component() {

    var previousPosition = Vector2f()
    val acceleration = Vector2f()

    var hasGravity = true

    override fun onAttach(ecs: EntityComponentSystem, entityID: Int) {
        val transformComponent = ecs.getComponent<TransformComponent>(entityID)

        if(transformComponent != null) {
            val pos = transformComponent.transform.position
            this.previousPosition.set(pos.x, pos.y)
        }
    }

    fun applyForce(force: Vector2f) {
        this.acceleration.add(force)
    }

    fun zeroAcceleration() { this.acceleration.zero() }

}