package com.paperclipengine.physics2d

import com.paperclipengine.scene.Component
import com.paperclipengine.scene.EntityComponentSystem
import com.paperclipengine.scene.TransformComponent
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType

class Rigidbody2D(private val physics2DWorld: Physics2DWorld) : Component() {

    lateinit var physicsBody: Body

    override fun onAttach(ecs: EntityComponentSystem, entityID: Int) {
        super.onAttach(ecs, entityID)

        val transformComponent = ecs.getComponent<TransformComponent>(entityID)!!

        val bodyDef = BodyDef()
        bodyDef.position = Vec2(transformComponent.transform.position.x, transformComponent.transform.position.y)
        bodyDef.type = BodyType.DYNAMIC

        this.physicsBody = physics2DWorld.world.createBody(bodyDef)
    }

    override fun onDetach(ecs: EntityComponentSystem, entityID: Int) {
        super.onDetach(ecs, entityID)

        physics2DWorld.world.destroyBody(physicsBody)
    }

}