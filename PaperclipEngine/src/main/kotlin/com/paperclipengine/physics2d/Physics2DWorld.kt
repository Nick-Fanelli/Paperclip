package com.paperclipengine.physics2d

import com.paperclipengine.scene.ComponentPair
import com.paperclipengine.scene.EntityComponentSystem
import com.paperclipengine.scene.TransformComponent
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.*

class Physics2DWorld(private val entityComponentSystem: EntityComponentSystem) {

    private val physicsTimeStep = 1.0f / 60.0f

    val world = World(Vec2(0f, -9.8f))

    var velocityIterations = 8
    var positionIterations = 3

    private var physicsTime = 0.0f

    private var rigidbodyComponents = ArrayList<ComponentPair<Rigidbody2D, TransformComponent>>()

    init {
        entityComponentSystem.addComponentTypeListener<Rigidbody2D> {
            rigidbodyComponents = entityComponentSystem.getAllComponentsByType(TransformComponent::class)
        }
    }

    fun onUpdate(deltaTime: Float) {
        physicsTime += deltaTime

        while(physicsTime >= physicsTimeStep) {
            physicsTime -= physicsTimeStep
            world.step(physicsTimeStep, velocityIterations, positionIterations)

            rigidbodyComponents.forEach {
                it.second.transform.position.set(it.first.physicsBody.position.x, it.first.physicsBody.position.y, it.second.transform.position.z)
                it.second.transform.rotation = Math.toDegrees(it.first.physicsBody.angle.toDouble()).toFloat()
            }
        }

    }

}