package com.paperclipengine.physics2d

import com.paperclipengine.scene.EntityComponentSystem
import com.paperclipengine.scene.TransformComponent
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.*

data class PhysicsComponentData(val transformComponent: TransformComponent, val rigidbody2D: Rigidbody2D, var circleCollider: CircleCollider? = null)

class Physics2DWorld(private val entityComponentSystem: EntityComponentSystem) {

    private val world: World = World(Vec2(0.0f, -9.8f))

    private val physicsTimeStep = 1.0f / 60.0f

    fun test() {
        val bodyDef = BodyDef()
        val body = world.createBody(bodyDef)
        body.type = BodyType.DYNAMIC

        val shape = PolygonShape()
        shape.setAsBox(1.0f, 1.0f)

        body.createFixture(shape, 0.0f)
    }

    fun onUpdate(deltaTime: Float) {

    }

//    fun setGravity(gravity: Vector2f) { this.world.gravity = Vec2(gravity.x, gravity.y) }

}