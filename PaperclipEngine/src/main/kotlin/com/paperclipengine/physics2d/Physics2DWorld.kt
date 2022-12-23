package com.paperclipengine.physics2d

import com.paperclipengine.scene.EntityComponentSystem
import com.paperclipengine.scene.TransformComponent
import org.joml.Vector2f
import org.joml.Vector3f

data class PhysicsComponentData(val transformComponent: TransformComponent, val rigidbody2D: Rigidbody2D, var circleCollider: CircleCollider? = null)

class Physics2DWorld(private val entityComponentSystem: EntityComponentSystem) {

    var gravity = Vector2f(0f, -9.8f)

    private val physicsTimeStep = 1.0f / 60.0f

    private var physicsComponentData: ArrayList<PhysicsComponentData> = ArrayList()

    private var physicsTime = 0.0f

    init {
        entityComponentSystem.addComponentTypeListener<Rigidbody2D> { updatePhysicsComponentData() }
        entityComponentSystem.addComponentTypeListener<CircleCollider> { updatePhysicsComponentData() }
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
            val data: PhysicsComponentData = PhysicsComponentData(it.second, it.first)
            data.circleCollider = entityComponentSystem.getComponent<CircleCollider>(it.entityID)

            physicsComponentData.add(data)
        }
    }

    private fun step() {
        physicsComponentData.forEach {
            updateEntity(it)
        }
    }

    private fun updateEntity(entity: PhysicsComponentData) {
        if(entity.rigidbody2D.rigidbodyType == RigidbodyType.STATIC)
            return

        applyConstraints(entity)

        if(entity.circleCollider != null)
            solveCollisions(entity)

        calculatePosition(entity)
    }

    private fun calculatePosition(entity: PhysicsComponentData) {
        if(entity.rigidbody2D.hasGravity)
            entity.rigidbody2D.applyForce(gravity)

        val velocity = Vector2f(entity.transformComponent.transform.position.x - entity.rigidbody2D.previousPosition.x,
            entity.transformComponent.transform.position.y - entity.rigidbody2D.previousPosition.y)

        entity.rigidbody2D.previousPosition = Vector2f(entity.transformComponent.transform.position.x, entity.transformComponent.transform.position.y)

        entity.transformComponent.transform.position.add(Vector3f(velocity.x + entity.rigidbody2D.acceleration.x * physicsTimeStep * physicsTimeStep,
            velocity.y + entity.rigidbody2D.acceleration.y * physicsTimeStep * physicsTimeStep, 0.0f))

        entity.rigidbody2D.zeroAcceleration()
    }

    private val position = Vector2f(0f, 0f)
    private val radius = 1.0f

    private fun applyConstraints(entity: PhysicsComponentData) {
        val toObj = Vector2f(entity.transformComponent.transform.position.x, entity.transformComponent.transform.position.y).sub(position)
        val distance = Vector2f.distance(entity.transformComponent.transform.position.x, entity.transformComponent.transform.position.y, position.x, position.y)

        if(distance > radius - 0.1f) {
            val n = Vector2f(toObj).div(distance)
            n.mul(radius - 0.1f)
            entity.transformComponent.transform.position.set(n.x, n.y, 0.0f)
        }
    }

    private val correction = 0.3f

    private fun solveCollisions(entity: PhysicsComponentData) {

        physicsComponentData.forEach {
            if(it != entity) {

                val collisionAxis = Vector2f(
                    entity.transformComponent.transform.position.x,
                    entity.transformComponent.transform.position.y
                )
                    .sub(it.transformComponent.transform.position.x, it.transformComponent.transform.position.y)

                val distance = Vector2f.length(collisionAxis.x, collisionAxis.y)

                if (distance < entity.transformComponent.transform.scale.x) { // TODO: use calculated physics nodes

                    val n = collisionAxis.div(distance)
                    val delta = entity.transformComponent.transform.scale.x - distance
                    entity.transformComponent.transform.position.add(correction * delta * n.x, correction * delta * n.y, 0f)
                    it.transformComponent.transform.position.sub(correction * delta * n.x, correction * delta * n.y, 0f)
                }
            }
        }

    }

}