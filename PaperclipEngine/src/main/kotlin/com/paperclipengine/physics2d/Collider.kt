package com.paperclipengine.physics2d

import com.paperclipengine.math.Vector2f
import com.paperclipengine.scene.Component
import com.paperclipengine.scene.Entity
import com.paperclipengine.scene.EntityComponentSystem
import com.paperclipengine.scene.TransformComponent
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Fixture
import org.jbox2d.dynamics.FixtureDef
import javax.xml.crypto.dsig.Transform
import kotlin.properties.Delegates

abstract class Collider() : Component() {}

enum class BoxColliderInitializationType {
    AUTOMATICALLY_DETECT_SIZE,
    MANUALLY_SET_SIZE
}

class BoxCollider(private val boxColliderInitializationType: BoxColliderInitializationType = BoxColliderInitializationType.AUTOMATICALLY_DETECT_SIZE,
                  private var manualSize: Vector2f = Vector2f(1.0f)) : Collider() {

    lateinit var fixture: Fixture

    var skinBuffer = 0.0f

    override fun onAttach(ecs: EntityComponentSystem, entityID: Int) {
        super.onAttach(ecs, entityID)

        val rigidbody2D = ecs.getComponent<Rigidbody2D>(entityID) ?: throw RuntimeException("Entity must contain a rigidbody component first")

        val scale = if(boxColliderInitializationType == BoxColliderInitializationType.MANUALLY_SET_SIZE) { manualSize } else {
            ecs.getComponent<TransformComponent>(entityID)!!.transform.scale.toVector2f() }

        val shape = PolygonShape()
        val halfSize = scale * Vector2f(0.5f)
        shape.setAsBox(halfSize.x, halfSize.y, Vec2(0.0f, 0.0f), 0.0f)
        shape.m_radius = skinBuffer

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f

        this.fixture = rigidbody2D.physicsBody.createFixture(fixtureDef)
    }

    override fun onDetach(ecs: EntityComponentSystem, entityID: Int) {
        super.onDetach(ecs, entityID)

        val rigidbody2D = ecs.getComponent<Rigidbody2D>(entityID) ?: throw RuntimeException("Entity must contain a rigidbody component first")

        rigidbody2D.physicsBody.destroyFixture(this.fixture)
    }

}


class CircleCollider : Collider() {

    lateinit var fixture: Fixture

    override fun onAttach(ecs: EntityComponentSystem, entityID: Int) {
        super.onAttach(ecs, entityID)

        val rigidbody2D = ecs.getComponent<Rigidbody2D>(entityID) ?: throw RuntimeException("Entity must contain a rigidbody component first")

        val radius = ecs.getComponent<TransformComponent>(entityID)!!.transform.scale.x

        val shape = CircleShape()
        shape.m_radius = radius / 2.0f

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f

        this.fixture = rigidbody2D.physicsBody.createFixture(fixtureDef)
    }

    override fun onDetach(ecs: EntityComponentSystem, entityID: Int) {
        super.onDetach(ecs, entityID)

        val rigidbody2D = ecs.getComponent<Rigidbody2D>(entityID) ?: throw RuntimeException("Entity must contain a rigidbody component first")

        rigidbody2D.physicsBody.destroyFixture(this.fixture)
    }

}