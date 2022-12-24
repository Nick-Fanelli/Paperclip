package com.paperclipengine.physics2d

import com.paperclipengine.scene.Component
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef

enum class RigidbodyType {
    STATIC, DYNAMIC
}

class Rigidbody2D : Component() {

    lateinit var body: Body
        private set

    var rigidbodyType: RigidbodyType = RigidbodyType.DYNAMIC
        set(value) {
            field = value
            createBody()
        }

    init {

    }

    private fun createBody() {

        val bodyDef = BodyDef()

    }

}