package com.paperclipengine.physics2d

import com.paperclipengine.scene.Component
import com.paperclipengine.scene.EntityComponentSystem
import com.paperclipengine.scene.TransformComponent
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType

enum class RigidbodyType {
    STATIC, DYNAMIC, KINEMATIC
}

class Rigidbody2D(private val physics2DWorld: Physics2DWorld, private val rigidbodyType: RigidbodyType = RigidbodyType.DYNAMIC) : Component() {

    lateinit var physicsBody: Body

    var isBullet = false
    var angularDamping = 0.01f
    var linearDamping = 0.0f
    var gravityScale = 1.0f

    override fun onAttach(ecs: EntityComponentSystem, entityID: Int) {
        super.onAttach(ecs, entityID)

        val transformComponent = ecs.getComponent<TransformComponent>(entityID)!!

        val bodyDef = BodyDef()
        bodyDef.angle = Math.toRadians(transformComponent.transform.rotation.toDouble()).toFloat()
        bodyDef.position = Vec2(transformComponent.transform.position.x, transformComponent.transform.position.y)
        bodyDef.angularDamping = this.angularDamping
        bodyDef.linearDamping = this.linearDamping
        bodyDef.bullet = this.isBullet
        bodyDef.gravityScale = this.gravityScale
        bodyDef.angularVelocity = 0.0f

        when(rigidbodyType) {
            RigidbodyType.STATIC -> bodyDef.type = BodyType.STATIC
            RigidbodyType.DYNAMIC -> bodyDef.type = BodyType.DYNAMIC
            RigidbodyType.KINEMATIC -> bodyDef.type = BodyType.KINEMATIC
        }

        this.physicsBody = physics2DWorld.world.createBody(bodyDef)
    }

    override fun onDetach(ecs: EntityComponentSystem, entityID: Int) {
        super.onDetach(ecs, entityID)

        physics2DWorld.world.destroyBody(physicsBody)
    }

    fun applyTorque(torque: Float) {
        this.physicsBody.applyTorque(torque)
    }

}